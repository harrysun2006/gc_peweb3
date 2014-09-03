package com.gc.util
{
  import com.gc.Constants;

  import mx.formatters.DateFormatter;
  import mx.utils.StringUtil;

  public class DateUtil
  {

    public static const DATE_SKIP:int=0;
    public static const DATE_NOW:int=1;
    public static const DATE_BEGIN:int=2;
    public static const DATE_END:int=3;

    public static const YEAR:int=0;
    public static const MONTH:int=1;
    public static const DATE:int=2;
    public static const HOUR:int=3;
    public static const MINUTE:int=4;
    public static const SECOND:int=5;
    public static const MILLISECOND:int=6;
    public static const WEEK:int=7;

    /**
     * Time facts
     */
    public static const SECONDS_IN_HOUR:int		= 3600;
    public static const SECONDS_IN_DAY:int		= 86400;
    public static const MILISECONDS_IN_DAY:int  = 86400000;
    public static const MINUTES_IN_DAY:int		= 1440;

    private static function singleton():void
    {
    }

    public function DateUtil(caller:Function=null)
    {
      if (caller != singleton)
        throw new Error("DateUtil is a non-instance class!!!");
    }

    /** date formater */
    public static var DATE_FORMATTER:DateFormatter=new DateFormatter();
    public static var DATETIME_FORMATTER:DateFormatter=new DateFormatter();
    DATE_FORMATTER.formatString=Constants.DATE_FORMAT;
    DATETIME_FORMATTER.formatString=Constants.DATETIME_FORMAT;

    public static function add(date:Date, n:int=1, field:int=DATE):Date
    {
      if (field < YEAR || field > WEEK)
        field = DATE;
      var r:Date=new Date(date.fullYear, date.month, date.date, date.hours, date.minutes, date.seconds, date.milliseconds);
      (field == YEAR) ? r.fullYear += n
        : (field == MONTH) ? r.month += n
        : (field == DATE) ? r.date += n
        : (field == HOUR) ? r.hours += n
        : (field == MINUTE) ? r.minutes += n
        : (field == SECOND) ? r.seconds += n
        : (field == MILLISECOND) ? r.milliseconds += n
        : (field == WEEK) ? r.date += n * Constants.DAY_PER_WEEK : 1;
      return r;
    }

    public static function sub(date:Date, n:int=1, field:int=DATE):Date
    {
      return add(date, -n, field);
    }

    public static function same(obj1:Object, obj2:Object, full:Boolean=true):Boolean
    {
      if (obj1 == null && obj2 == null)
        return true;
      if (obj1 is Date && obj2 is Date)
      {
        var d1:Date=obj1 as Date;
        var d2:Date=obj2 as Date;
        if (full)
          return d1.time == d2.time;
        else
          return (d1.fullYear == d2.fullYear && d1.month == d2.month && d1.date == d2.date);
      }
      return false;
    }

    /**
     * Date Formatting Functions
     */
    public static function formatDate(date:Object, formatString:String=null):String
    {
      var df:DateFormatter;
      if (formatString == null)
        df=DATE_FORMATTER;
      else
      {
        df=new DateFormatter();
        df.formatString=formatString;
      }
      return (date == null) ? "" : df.format(date);
    }

    public static function formatDateTime(date:Object, formatString:String=null):String
    {
      var df:DateFormatter;
      if (formatString == null)
        df=DATETIME_FORMATTER;
      else
      {
        df=new DateFormatter();
        df.formatString=formatString;
      }
      return (date == null) ? "" : df.format(date);
    }

    public static function parseDate(dateStr:String, type:int=DATE_SKIP):Date
    {
      dateStr=dateStr.replace(/-/g, "/");
      dateStr=dateStr.replace("T", " ");
      dateStr=dateStr.replace("Z", " ");
      const i:Number=Date.parse(dateStr);
      var date:Date=null;
      date=new Date(i);
      switch (type)
      {
        case DATE_BEGIN:
          date.hours=date.minutes=date.seconds=date.milliseconds=0;
          break;
        case DATE_END:
          date.hours=23;
          date.minutes=59;
          date.seconds=59;
          date.milliseconds=999;
          break;
        case DATE_SKIP:
        case DATE_NOW:
        default:
          break;
      }
      return date;
    }

    public static function getBeginYear(date:Date, years:int):Date
    {
      var year:int=years * (int(date.fullYear / years));
      return new Date(year, 0);
    }

    public static function getEndYear(date:Date, years:int):Date
    {
      var year:int=years * (int(date.fullYear / years) + 1) - 1;
      return new Date(year, 11, 31, 23, 59, 59, 999);
    }

    public static function getBeginDate(date:Date, field:int=MONTH):Date
    {
      field=field < YEAR ? YEAR : field > SECOND ? SECOND : field;
      if (field == YEAR)
        return new Date(date.fullYear, 0, 1);
      else if (field == MONTH)
        return new Date(date.fullYear, date.month, 1);
      else if (field == DATE)
        return new Date(date.fullYear, date.month, date.date, 0);
      else if (field == HOUR)
        return new Date(date.fullYear, date.month, date.date, date.hours, 0);
      else if (field == MINUTE)
        return new Date(date.fullYear, date.month, date.date, date.hours, date.minutes, 0);
      else
        return new Date(date.fullYear, date.month, date.date, date.hours, date.minutes, date.seconds, 0);
    }

    public static function getEndDate(date:Date, field:int=MONTH):Date
    {
      field=field < YEAR ? YEAR : field > SECOND ? SECOND : field;
      var r:Date=new Date(date.fullYear, date.month, date.date, date.hours, date.minutes, date.seconds, date.milliseconds);
      if (field == YEAR)
        r.fullYear++;
      else if (field == MONTH)
        r.month++;
      else if (field == DATE)
        r.date++;
      else if (field == HOUR)
        r.hours++;
      else if (field == MINUTE)
        r.minutes++;
      else
        r.seconds++;
      r=getBeginDate(r, field);
      r.milliseconds--;
      return r;
    }

    /**
     * 返回date所在月的天数
     **/
    public static function getDaysOfMonth(date:Date):int
    {
      var edate:Date=getEndDate(date);
      return edate.date;
    }

    /**
     * 返回date所在月的周数
     * firstDay: 0 - Sunday, 1 - Monday, ...
     **/
    public static function getWeeksOfMonth(date:Date, firstDay:int=1):int
    {
      var d1:Date=getBeginDate(date);
      var d2:Date=new Date(d1.fullYear, d1.month, d1.date);
      d2.date += (firstDay - d1.day + Constants.DAY_PER_WEEK) % Constants.DAY_PER_WEEK;
      var d3:Date=getEndDate(date);
      return int((d3.date - d2.date) / Constants.DAY_PER_WEEK + 1) + (d1.day == firstDay ? 0 : 1);
    }

    /**
     * 返回date在当月是第几周
     **/
    public static function getWeekByMonth(date:Date, firstDay:int=1):int
    {
      var d1:Date=getBeginDate(date);
      var d2:Date=new Date(d1.fullYear, d1.month, d1.date);
      d2.date += (firstDay - d1.day + Constants.DAY_PER_WEEK) % Constants.DAY_PER_WEEK;
      return int((date.date - d2.date) / Constants.DAY_PER_WEEK + 1) + (d1.day == firstDay ? 0 : 1);
    }

    /**
     * 返回date所在年的天数
     **/
    public static function getDaysOfYear(date:Date):int
    {
      var d1:Date=getBeginDate(date, YEAR);
      var d2:Date=getEndDate(date, YEAR);
      return int((d2.time-d1.time+1)/Constants.MS_PER_DAY);
    }

    /**
     * 返回date所在年的周数
     **/
    public static function getWeeksOfYear(date:Date, firstDay:int=1):int
    {
      var d1:Date=getBeginDate(date, YEAR);
      var d2:Date=new Date(d1.fullYear, d1.month, d1.date);
      d2.date += (firstDay - d1.day + Constants.DAY_PER_WEEK) % Constants.DAY_PER_WEEK;
      var d3:int=getDaysOfYear(date);
      return int((d3 - d2.date + d1.date) / Constants.DAY_PER_WEEK + 1) + (d1.day == firstDay ? 0 : 1);
    }

    /**
     * 返回date在当年是第几周!
     **/
    public static function getWeekByYear(date:Date, firstDay:int=1):int
    {
      var d1:Date=getBeginDate(date, YEAR);
      var d2:Date=new Date(d1.fullYear, d1.month, d1.date);
      d2.date += (firstDay - d1.day + Constants.DAY_PER_WEEK) % Constants.DAY_PER_WEEK;
      var d3:int=(date.time - d2.time)/Constants.MS_PER_WEEK;
      return d3 + (d1.day == firstDay ? 0 : 1);
    }

    /**
     * time
     */
    public static function parseTime(value:String, guessPMBelow:int = 0):Date
    {
      value = StringUtil.trim(value.toUpperCase());
      var dt:Date = new Date(2000,0,1);
      var time:Object;
      var isMil:Boolean = false;
      //standard time regex
      var matches:Array;
      var reg:RegExp = /^(1[012]|[1-9])(:[0-5]\d)?(:[0-5]\d)?(\ ?[AaPp][Mm]?)?$/;
      matches = reg.exec(value);
      if (!matches)
      {
        //military time regex
        reg = /^(2[0-4]|1\d|0?\d)(:?[0-5]\d)?(:?[0-5]\d)?$/;
        isMil = true;
        matches = reg.exec(value);
      }
      if (!matches)
      {
        //could not parse
        return null;
      }
      time = {
          hours: Number(matches[1]),
          minutes: matches[2] ? Number(String(matches[2]).replace(':','')) : 0,
          seconds: matches[3] ? Number(String(matches[3]).replace(':','')) : 0,
          ampm: null
        };
      if (isMil)
      {
        //processing military format
        dt.setHours(time.hours, time.minutes, time.seconds);
      }
      else
      {
        //processing common format
        if (matches[4])
        {
          //user indicated AM/PM
          if (String(matches[4]).indexOf('P') != -1)
          {
            //PM
            time.hours = time.hours == 12 ? 12 : time.hours + 12;
          }
          else if (time.hours == 12)
          {
            time.hours = 0;
          }
        }
        else if (guessPMBelow > 0)
        {
          //will have to guess PM
          time.hours = time.hours < guessPMBelow ? time.hours + 12 : time.hours;
        }
      }
      dt.setHours(time.hours, time.minutes, time.seconds);
      return dt;
    }

    public static function toSeconds(value:Object):Number
    {
      var d:Date;
      if (value is Date)
      {
        d = value as Date;
      }
      else
      {
        d = parseDate(value.toString());
      }
      if (!d)
      {
        return -1;
      }
      else
      {
        return (d.getHours() * 3600) + (d.getMinutes() * 60) + d.getSeconds();
      }
    }

    public static function toMinutes(value:Object):Number
    {
      var sec:Number = toSeconds(value);
      if (sec == -1)
      {
        return -1;
      }
      else
      {
        return sec / 60;
      }
    }

    public static function toHours(value:Object):Number
    {
      var sec:Number = toSeconds(value);
      if (sec == -1)
      {
        return -1;
      }
      else
      {
        return sec / DateUtil.SECONDS_IN_HOUR;
      }
    }

    public static function formatDuration(n:Number, unit:int=MONTH):String
    {
      var y:int, m:int, d:int;
      var r:String="";
      if (unit == MONTH)
      {
        y=n/12;
        m=n%12;
        r=y > 0 ? LangUtil.getString("gcc", "duration.format.ym", [y,m]) 
          : LangUtil.getString("gcc", "duration.format.m", [m]);
      }
      return r;
    }

    // 返回d1, d2之间相差的年/月/周/日数(d1 < d2, d2=null表示当前系统时间)
    public static function getDiff(d1:Date, d2:Date=null, unit:int=MONTH):int
    {
      d1=d1 ? d1 : Constants.MIN_DATE;
      d2=d2 ? d2 : new Date();
      var y:int, m:int, d:int;
      var r:int=0;
      if (unit == MONTH)
      {
        y=d2.fullYear-d1.fullYear;
        m=d2.month-d1.month;
        d=d2.date-d1.date;
        m=(d <= 0) ? m-1 : m;
        r=y*12+m;
      }
      return r;
    }
  }
}