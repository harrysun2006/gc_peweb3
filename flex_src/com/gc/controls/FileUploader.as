package com.gc.controls
{
	import mx.controls.DataGrid;
	import mx.controls.Button;
	import mx.controls.ProgressBar;
	import mx.controls.ProgressBarMode;
	import mx.controls.dataGridClasses.*;
	import mx.controls.Alert;
	import mx.events.ItemClickEvent;
	import mx.events.CollectionEvent;
	import mx.collections.ArrayCollection;
	import flash.events.*;
	import flash.net.FileReferenceList;
	import flash.net.FileFilter;
	import flash.net.FileReference;
	import flash.net.URLRequest;
	import flash.net.URLVariables;

	public class FileUploader extends TitleWindow
	{

		// Events
		public var completeEvent:Event;

		// UI components
		private var _dgFiles:DataGrid;
		private var _btBrowse:Button;
		private var _btUpload:Button;
		private var _pbUpload:ProgressBar;

		// DataGrid columns
		private var _nameColumn:DataGridColumn;
		private var _typeColumn:DataGridColumn;
		private var _sizeColumn:DataGridColumn;
		private var _creationDate:DataGridColumn;
		private var _modificationDate:DataGridColumn;
		private var _progressColumn:DataGridColumn;
		private var _columns:Array;

		// File references
		[Bindable]
		private var _files:ArrayCollection;
		private var _fileref:FileReferenceList
		private var _file:FileReference;
		private var _uploadURL:URLRequest;
		public var  _totalbytes:Number;

		// File filters
		private var _filefilter:Array;
		private var imageTypes:FileFilter = new FileFilter("Images (*.jpg; *.jpeg; *.gif; *.png)" ,"*.jpg; *.jpeg; *.gif; *.png");
		private var videoTypes:FileFilter = new FileFilter("Flash Video Files (*.flv)","*.flv");
		private var documentTypes:FileFilter = new FileFilter("Documents (*.pdf)",("*.pdf"));
		private var allTypes:Array = new Array(imageTypes,videoTypes,documentTypes);

		// config vars
		private var _url:String; // location of the file upload handler can be a relative path or FQDM
		private var _maxFileSize:Number; //bytes

		private var _variables:URLVariables; //variables to passed along to the file upload handler on the server.

		[Inspectable(category="General",defaultValue="true")]
		public function get showError():Boolean
		{
			return _showError;
		}

		public function set showError(value:Boolean):void
		{
			var old:Boolean=this._showError;
			this._showError=value;
			if (old != value)
			{
				this.invalidateDisplayList();
			}
		}

		//Constructor
		public function FileUploader()
		{
			_datagrid = dataGrid;
			_browsebutton = browseButton;
			_remallbutton = removeAllButton;
			_remselbutton = removeSelectedButton;
			_uploadbutton = uploadButton;
			_url = url;
			_progressbar = progressBar;
			_variables = variables;
			_maxFileSize = maxFileSize;
			init();
		}

		//Initialize    
		private function init():void
		{

			_files = new ArrayCollection();
			_fileref = new FileReferenceList;
			_file = new FileReference;
			_totalbytes = 0;
			_browsebutton.addEventListener(MouseEvent.CLICK, browseFiles);
			_uploadbutton.addEventListener(MouseEvent.CLICK,uploadFiles);
			_remallbutton.addEventListener(MouseEvent.CLICK,clearFileQue);
			_remselbutton.addEventListener(MouseEvent.CLICK,removeSelectedFileFromQue);
			_fileref.addEventListener(Event.SELECT, selectHandler);
			_files.addEventListener(CollectionEvent.COLLECTION_CHANGE,popDataGrid);

			_nameColumn = new DataGridColumn;
			_typeColumn = new DataGridColumn;
			_sizeColumn = new DataGridColumn;

			_nameColumn.dataField = "name";
			_nameColumn.headerText= "File";

			_typeColumn.dataField = "type";
			_typeColumn.headerText = "File Type";
			_typeColumn.width = 80;

			_sizeColumn.dataField = "size";
			_sizeColumn.headerText = "File Size";
			_sizeColumn.labelFunction = bytesToKilobytes as Function;
			_sizeColumn.width = 150;

			_progressbar.mode = "manual";
			_progressbar.label = "";

			_uploadbutton.enabled = false;
			_remselbutton.enabled = false;
			_remallbutton.enabled = false;

			_columns = new Array(_nameColumn,_typeColumn,_sizeColumn);
			_datagrid.columns = _columns
			_datagrid.sortableColumns = false;
			_datagrid.dataProvider = _files;
			_datagrid.dragEnabled = true;
			_datagrid.dragMoveEnabled = true;
			_datagrid.dropEnabled = true;

			_uploadURL = new URLRequest;
			_uploadURL.url = _url;
			_uploadURL.method = "GET";
			_uploadURL.data = _variables;
			_uploadURL.contentType = "multipart/form-data";
		}

		/********************************************************
		 *   METHODS                                             *
		 ********************************************************/

		private function browseFiles(event:Event):void
		{
			//browse for files
			_fileref.browse(allTypes);

		}

		private function uploadFiles(event:Event):void
		{

			if (_files.length > 0)
			{
				_file = FileReference(_files.getItemAt(0));
				_file.addEventListener(Event.OPEN, openHandler);
				_file.addEventListener(ProgressEvent.PROGRESS, progressHandler);
				_file.addEventListener(Event.COMPLETE, completeHandler);
				_file.addEventListener(SecurityErrorEvent.SECURITY_ERROR,securityErrorHandler);
				_file.addEventListener(HTTPStatusEvent.HTTP_STATUS,httpStatusHandler);
				_file.addEventListener(IOErrorEvent.IO_ERROR,ioErrorHandler);
				_file.upload(_uploadURL);
				setupCancelButton(true);
			}
		}

		private function removeSelectedFileFromQue(event:Event):void
		{

			if (_datagrid.selectedIndex >= 0)
			{
				_files.removeItemAt( _datagrid.selectedIndex);
			}
		}

		private function clearFileQue(event:Event):void
		{
			//remove all files from the upload que;
			_files.removeAll();
		}

		private function cancelFileIO(event:Event):void
		{

			_file.cancel();
			setupCancelButton(false);
			checkQue();

		}



		/********************************************************
		 *  Logic Functions                                      *
		 ********************************************************/



		//label function for the datagird File Size Column
		private function bytesToKilobytes(data:Object,blank:Object):String
		{
			var kilobytes:String;
			kilobytes = String(Math.round(data.size/ 1024)) + ' kb';
			return kilobytes
		}


		// Feed the progress bar a meaningful label
		private function getByteCount():void
		{
			var i:int;
			_totalbytes = 0;
			for (i=0; i < _files.length; i++)
			{
				_totalbytes +=  _files[i].size;
			}
			_progressbar.label = "Total Files: "+  _files.length+ " Total Size: " + Math.round(_totalbytes/1024) + " kb"
		}

		// Checks the files do not exceed maxFileSize | if _maxFileSize == 0 No File Limit Set
		private function checkFileSize(filesize:Number):Boolean
		{

			var r:Boolean = false;
			//if  filesize greater then _maxFileSize
			if (filesize > _maxFileSize)
			{
				r = false;
				trace("false");
			}
			else if (filesize <= _maxFileSize)
			{
				r = true;
				trace("true");
			}

			if (_maxFileSize == 0)
			{
				r = true;
			}

			return r;
		}

		// restores progress bar back to normal
		private function resetProgressBar():void
		{

			_progressbar.label = "";
			_progressbar.maximum = 0;
			_progressbar.minimum = 0;
		}

		// reset form item elements
		private function resetForm():void
		{
			_uploadbutton.enabled = false;
			_uploadbutton.addEventListener(MouseEvent.CLICK,uploadFiles);
			_uploadbutton.label = "Upload";
			_progressbar.maximum = 0;
			_totalbytes = 0;
			_progressbar.label = "";
			_remselbutton.enabled = false;
			_remallbutton.enabled = false;
			_browsebutton.enabled = true;
		}

		// whenever the _files arraycollection changes this function is called to make sure the datagrid data jives
		private function popDataGrid(event:CollectionEvent):void
		{
			getByteCount();
			checkQue();
		}

		// enable or disable upload and remove controls based on files in the cue;        
		private function checkQue():void
		{
			if (_files.length > 0)
			{
				_uploadbutton.enabled = true;
				_remselbutton.enabled = true;
				_remallbutton.enabled = true;
			}
			else
			{
				resetProgressBar();
				_uploadbutton.enabled = false;
			}
		}

		// toggle upload button label and function to trigger file uploading or upload cancelling
		private function setupCancelButton(x:Boolean):void
		{
			if (x == true)
			{
				_uploadbutton.label = "Cancel";
				_browsebutton.enabled = false;
				_remselbutton.enabled = false;
				_remallbutton.enabled = false;
				_uploadbutton.addEventListener(MouseEvent.CLICK,cancelFileIO);
			}
			else if (x == false)
			{
				_uploadbutton.removeEventListener(MouseEvent.CLICK,cancelFileIO);
				resetForm();
			}
		}


		/*********************************************************
		 *   Event Handlers                                      *
		 *********************************************************/

		//  called after user selected files form the browse dialouge box.
		private function selectHandler(event:Event):void
		{
			var i:int;
			var msg:String ="";
			var dl:Array = [];
			for (i=0; i < event.currentTarget.fileList.length; i ++)
			{
				if (checkFileSize(event.currentTarget.fileList[i].size))
				{
					_files.addItem(event.currentTarget.fileList[i]);
					trace("under size " + event.currentTarget.fileList[i].size);
				}
				else
				{
					dl.push(event.currentTarget.fileList[i]);
					trace(event.currentTarget.fileList[i].name + " too large");
				}
			}
			if (dl.length > 0)
			{
				for (i=0; i<dl.length; i++)
				{
					msg += String(dl[i].name + " is too large. \n");
				}
				mx.controls.Alert.show(msg + "Max File Size is: " + Math.round(_maxFileSize / 1024) + " kb","File Too Large",4,null).clipContent;
			}
		}

		// called after the file is opened before upload    
		private function openHandler(event:Event):void
		{
			trace('openHandler triggered');
			_files;
		}

		// called during the file upload of each file being uploaded | we use this to feed the progress bar its data
		private function progressHandler(event:ProgressEvent):void
		{
			_progressbar.setProgress(event.bytesLoaded,event.bytesTotal);
			_progressbar.label = "Uploading " + Math.round(event.bytesLoaded / 1024) + " kb of " + Math.round(event.bytesTotal / 1024) + " kb " + (_files.length - 1) + " files remaining";
		}

		// called after a file has been successully uploaded | we use this as well to check if there are any files left to upload and how to handle it
		private function completeHandler(event:Event):void
		{
			//trace('completeHanderl triggered');
			_files.removeItemAt(0);
			if (_files.length > 0)
			{
				_totalbytes = 0;
				uploadFiles(null);
			}
			else
			{
				setupCancelButton(false);
				_progressbar.label = "Uploads Complete";
				var uploadCompleted:Event = new Event(Event.COMPLETE);
				dispatchEvent(uploadCompleted);
			}
		}

		// only called if there is an  error detected by flash player browsing or uploading a file   
		private function ioErrorHandler(event:IOErrorEvent):void
		{
			//trace('And IO Error has occured:' +  event);
			mx.controls.Alert.show("ioError",String(event),0);
		}

		// only called if a security error detected by flash player such as a sandbox violation
		private function securityErrorHandler(event:SecurityErrorEvent):void
		{
			//trace("securityErrorHandler: " + event);
			mx.controls.Alert.show("Security Error",String(event),0);
		}

		//  This function its not required
		private function cancelHandler(event:Event):void
		{
			// cancel button has been clicked;
			trace('cancelled');
		}

		//  after a file upload is complete or attemted the server will return an http status code, code 200 means all is good anything else is bad.
		private function httpStatusHandler(event:HTTPStatusEvent):void
		{
			//        trace("httpStatusHandler: " + event);
			if (event.status != 200)
			{
				mx.controls.Alert.show("Error",String(event),0);
			}
		}


	}
}