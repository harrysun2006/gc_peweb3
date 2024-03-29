package com.gc.util
{
  public class Codepage
  {
    private static function singleton():void
    {
    }

    private static const CODEPAGES:Object = 
      {
        037:{id:037, charset:"IBM037", name:"IBM EBCDIC US-Canada"},
        437:{id:437, charset:"IBM437", name:"OEM United States"},
        500:{id:500, charset:"IBM500", name:"IBM EBCDIC International"},
        708:{id:708, charset:"ASMO-708", name:"Arabic (ASMO 708)"},
        709:{id:709, charset:"", name:"Arabic (ASMO-449+, BCON V4)"},
        710:{id:701, charset:"", name:"Arabic - Transparent Arabic"},
        720:{id:720, charset:"DOS-720", name:"Arabic (Transparent ASMO); Arabic (DOS)"},
        737:{id:737, charset:"ibm737", name:"OEM Greek (formerly 437G); Greek (DOS)"},
        775:{id:775, charset:"ibm775", name:"OEM Baltic; Baltic (DOS)"},
        850:{id:850, charset:"ibm850", name:"OEM Multilingual Latin 1; Western European (DOS)"},
        852:{id:852, charset:"ibm852", name:"OEM Latin 2; Central European (DOS)"},
        855:{id:855, charset:"IBM855", name:"OEM Cyrillic (primarily Russian)"},
        857:{id:857, charset:"ibm857", name:"OEM Turkish; Turkish (DOS)"},
        858:{id:858, charset:"IBM00858", name:"OEM Multilingual Latin 1 + Euro symbol"},
        860:{id:860, charset:"IBM860", name:"OEM Portuguese; Portuguese (DOS)"},
        861:{id:861, charset:"ibm861", name:"OEM Icelandic; Icelandic (DOS)"},
        862:{id:862, charset:"DOS-862", name:"OEM Hebrew; Hebrew (DOS)"},
        863:{id:863, charset:"IBM863", name:"OEM French Canadian; French Canadian (DOS)"},
        864:{id:864, charset:"IBM864", name:"OEM Arabic; Arabic (864)"},
        865:{id:865, charset:"IBM865", name:"OEM Nordic; Nordic (DOS)"},
        866:{id:866, charset:"cp866", name:"OEM Russian; Cyrillic (DOS)"},
        869:{id:869, charset:"ibm869", name:"OEM Modern Greek; Greek, Modern (DOS)"},
        870:{id:870, charset:"IBM870", name:"IBM EBCDIC Multilingual/ROECE (Latin 2); IBM EBCDIC Multilingual Latin 2"},
        874:{id:874, charset:"windows-874", name:"ANSI/OEM Thai (same as 28605, ISO 8859-15); Thai (Windows)"}
      };

    /*
       875	cp875	IBM EBCDIC Greek Modern
       932	shift_jis	ANSI/OEM Japanese; Japanese (Shift-JIS)
       936	gb2312	ANSI/OEM Simplified Chinese (PRC, Singapore); Chinese Simplified (GB2312)
       949	ks_c_5601-1987	ANSI/OEM Korean (Unified Hangul Code)
       950	big5	ANSI/OEM Traditional Chinese (Taiwan; Hong Kong SAR, PRC); Chinese Traditional (Big5)
       1026	IBM1026	IBM EBCDIC Turkish (Latin 5)
       1047	IBM01047	IBM EBCDIC Latin 1/Open System
       1140	IBM01140	IBM EBCDIC US-Canada (037 + Euro symbol); IBM EBCDIC (US-Canada-Euro)
       1141	IBM01141	IBM EBCDIC Germany (20273 + Euro symbol); IBM EBCDIC (Germany-Euro)
       1142	IBM01142	IBM EBCDIC Denmark-Norway (20277 + Euro symbol); IBM EBCDIC (Denmark-Norway-Euro)
       1143	IBM01143	IBM EBCDIC Finland-Sweden (20278 + Euro symbol); IBM EBCDIC (Finland-Sweden-Euro)
       1144	IBM01144	IBM EBCDIC Italy (20280 + Euro symbol); IBM EBCDIC (Italy-Euro)
       1145	IBM01145	IBM EBCDIC Latin America-Spain (20284 + Euro symbol); IBM EBCDIC (Spain-Euro)
       1146	IBM01146	IBM EBCDIC United Kingdom (20285 + Euro symbol); IBM EBCDIC (UK-Euro)
       1147	IBM01147	IBM EBCDIC France (20297 + Euro symbol); IBM EBCDIC (France-Euro)
       1148	IBM01148	IBM EBCDIC International (500 + Euro symbol); IBM EBCDIC (International-Euro)
       1149	IBM01149	IBM EBCDIC Icelandic (20871 + Euro symbol); IBM EBCDIC (Icelandic-Euro)
       1200	utf-16	Unicode UTF-16, little endian byte order (BMP of ISO 10646); available only to managed applications
       1201	unicodeFFFE	Unicode UTF-16, big endian byte order; available only to managed applications
       1250	windows-1250	ANSI Central European; Central European (Windows)
       1251	windows-1251	ANSI Cyrillic; Cyrillic (Windows)
       1252	windows-1252	ANSI Latin 1; Western European (Windows)
       1253	windows-1253	ANSI Greek; Greek (Windows)
       1254	windows-1254	ANSI Turkish; Turkish (Windows)
       1255	windows-1255	ANSI Hebrew; Hebrew (Windows)
       1256	windows-1256	ANSI Arabic; Arabic (Windows)
       1257	windows-1257	ANSI Baltic; Baltic (Windows)
       1258	windows-1258	ANSI/OEM Vietnamese; Vietnamese (Windows)
       1361	Johab	Korean (Johab)
       10000	macintosh	MAC Roman; Western European (Mac)
       10001	x-mac-japanese	Japanese (Mac)
       10002	x-mac-chinesetrad	MAC Traditional Chinese (Big5); Chinese Traditional (Mac)
       10003	x-mac-korean	Korean (Mac)
       10004	x-mac-arabic	Arabic (Mac)
       10005	x-mac-hebrew	Hebrew (Mac)
       10006	x-mac-greek	Greek (Mac)
       10007	x-mac-cyrillic	Cyrillic (Mac)
       10008	x-mac-chinesesimp	MAC Simplified Chinese (GB 2312); Chinese Simplified (Mac)
       10010	x-mac-romanian	Romanian (Mac)
       10017	x-mac-ukrainian	Ukrainian (Mac)
       10021	x-mac-thai	Thai (Mac)
       10029	x-mac-ce	MAC Latin 2; Central European (Mac)
       10079	x-mac-icelandic	Icelandic (Mac)
       10081	x-mac-turkish	Turkish (Mac)
       10082	x-mac-croatian	Croatian (Mac)
       12000	utf-32	Unicode UTF-32, little endian byte order; available only to managed applications
       12001	utf-32BE	Unicode UTF-32, big endian byte order; available only to managed applications
       20000	x-Chinese_CNS	CNS Taiwan; Chinese Traditional (CNS)
       20001	x-cp20001	TCA Taiwan
       20002	x_Chinese-Eten	Eten Taiwan; Chinese Traditional (Eten)
       20003	x-cp20003	IBM5550 Taiwan
       20004	x-cp20004	TeleText Taiwan
       20005	x-cp20005	Wang Taiwan
       20105	x-IA5	IA5 (IRV International Alphabet No. 5, 7-bit); Western European (IA5)
       20106	x-IA5-German	IA5 German (7-bit)
       20107	x-IA5-Swedish	IA5 Swedish (7-bit)
       20108	x-IA5-Norwegian	IA5 Norwegian (7-bit)
       20127	us-ascii	US-ASCII (7-bit)
       20261	x-cp20261	T.61
       20269	x-cp20269	ISO 6937 Non-Spacing Accent
       20273	IBM273	IBM EBCDIC Germany
       20277	IBM277	IBM EBCDIC Denmark-Norway
       20278	IBM278	IBM EBCDIC Finland-Sweden
       20280	IBM280	IBM EBCDIC Italy
       20284	IBM284	IBM EBCDIC Latin America-Spain
       20285	IBM285	IBM EBCDIC United Kingdom
       20290	IBM290	IBM EBCDIC Japanese Katakana Extended
       20297	IBM297	IBM EBCDIC France
       20420	IBM420	IBM EBCDIC Arabic
       20423	IBM423	IBM EBCDIC Greek
       20424	IBM424	IBM EBCDIC Hebrew
       20833	x-EBCDIC-KoreanExtended	IBM EBCDIC Korean Extended
       20838	IBM-Thai	IBM EBCDIC Thai
       20866	koi8-r	Russian (KOI8-R); Cyrillic (KOI8-R)
       20871	IBM871	IBM EBCDIC Icelandic
       20880	IBM880	IBM EBCDIC Cyrillic Russian
       20905	IBM905	IBM EBCDIC Turkish
       20924	IBM00924	IBM EBCDIC Latin 1/Open System (1047 + Euro symbol)
       20932	EUC-JP	Japanese (JIS 0208-1990 and 0121-1990)
       20936	x-cp20936	Simplified Chinese (GB2312); Chinese Simplified (GB2312-80)
       20949	x-cp20949	Korean Wansung
       21025	cp1025	IBM EBCDIC Cyrillic Serbian-Bulgarian
       21027		(deprecated)
       21866	koi8-u	Ukrainian (KOI8-U); Cyrillic (KOI8-U)
       28591	iso-8859-1	ISO 8859-1 Latin 1; Western European (ISO)
       28592	iso-8859-2	ISO 8859-2 Central European; Central European (ISO)
       28593	iso-8859-3	ISO 8859-3 Latin 3
       28594	iso-8859-4	ISO 8859-4 Baltic
       28595	iso-8859-5	ISO 8859-5 Cyrillic
       28596	iso-8859-6	ISO 8859-6 Arabic
       28597	iso-8859-7	ISO 8859-7 Greek
       28598	iso-8859-8	ISO 8859-8 Hebrew; Hebrew (ISO-Visual)
       28599	iso-8859-9	ISO 8859-9 Turkish
       28603	iso-8859-13	ISO 8859-13 Estonian
       28605	iso-8859-15	ISO 8859-15 Latin 9
       29001	x-Europa	Europa 3
       38598	iso-8859-8-i	ISO 8859-8 Hebrew; Hebrew (ISO-Logical)
       50220	iso-2022-jp	ISO 2022 Japanese with no halfwidth Katakana; Japanese (JIS)
       50221	csISO2022JP	ISO 2022 Japanese with halfwidth Katakana; Japanese (JIS-Allow 1 byte Kana)
       50222	iso-2022-jp	ISO 2022 Japanese JIS X 0201-1989; Japanese (JIS-Allow 1 byte Kana - SO/SI)
       50225	iso-2022-kr	ISO 2022 Korean
       50227	x-cp50227	ISO 2022 Simplified Chinese; Chinese Simplified (ISO 2022)
       50229		ISO 2022 Traditional Chinese
       50930		EBCDIC Japanese (Katakana) Extended
       50931		EBCDIC US-Canada and Japanese
       50933		EBCDIC Korean Extended and Korean
       50935		EBCDIC Simplified Chinese Extended and Simplified Chinese
       50936		EBCDIC Simplified Chinese
       50937		EBCDIC US-Canada and Traditional Chinese
       50939		EBCDIC Japanese (Latin) Extended and Japanese
       51932	euc-jp	EUC Japanese
       51936	EUC-CN	EUC Simplified Chinese; Chinese Simplified (EUC)
       51949	euc-kr	EUC Korean
       51950		EUC Traditional Chinese
       52936	hz-gb-2312	HZ-GB2312 Simplified Chinese; Chinese Simplified (HZ)
       54936	GB18030	Windows XP and later: GB18030 Simplified Chinese (4 byte); Chinese Simplified (GB18030)
       57002	x-iscii-de	ISCII Devanagari
       57003	x-iscii-be	ISCII Bengali
       57004	x-iscii-ta	ISCII Tamil
       57005	x-iscii-te	ISCII Telugu
       57006	x-iscii-as	ISCII Assamese
       57007	x-iscii-or	ISCII Oriya
       57008	x-iscii-ka	ISCII Kannada
       57009	x-iscii-ma	ISCII Malayalam
       57010	x-iscii-gu	ISCII Gujarati
       57011	x-iscii-pa	ISCII Punjabi
       65000	utf-7	Unicode (UTF-7)
       65001	utf-8	Unicode (UTF-8)
     */

    public function Codepage(caller:Function=null)
    {
      if (caller != singleton)
        throw new Error("Codepage is a non-instance class!!!");
    }



  }
}