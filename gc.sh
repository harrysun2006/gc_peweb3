#!/bin/sh
# -----------------------------------------------------------------------------
# Run Script for the JantekDemo
#
# Environment Variable Prequisites
#
#   JAVA_HOME       Must point at your Java Development Kit installation.
#                   Required to run the with the "debug" or "javac" argument.
#
#   JRE_HOME        Must point at your Java Development Kit installation.
#                   Defaults to JAVA_HOME if empty.
#
#   JAVA_OPTS       (Optional) Java runtime options used when the tool runs.
#
#
# -----------------------------------------------------------------------------

# OS specific support.  $var _must_ be set to either true or false.
cygwin=false
os400=false
darwin=false
case "`uname`" in
CYGWIN*) cygwin=true;;
OS400*) os400=true;;
Darwin*) darwin=true;;
esac

# resolve links - $0 may be a softlink
PRG="$0"

while [ -h "$PRG" ]; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done

# Get standard environment variables
PRGDIR=`dirname "$PRG"`

# For Cygwin, ensure paths are in UNIX format before anything is touched
if $cygwin; then
  [ -n "$JAVA_HOME" ] && JAVA_HOME=`cygpath --unix "$JAVA_HOME"`
fi

# For OS400
if $os400; then
  # Set job priority to standard for interactive (interactive - 6) by using
  # the interactive priority - 6, the helper threads that respond to requests
  # will be running at the same priority as interactive jobs.
  COMMAND='chgjob job('$JOBNAME') runpty(6)'
  system $COMMAND

  # Enable multi threading
  export QIBM_MULTI_THREADED=Y
fi

# Make sure prerequisite environment variables are set
if [ -z "$JAVA_HOME" -a -z "$JRE_HOME" ]; then
  if $darwin && [ -d "/System/Library/Frameworks/JavaVM.framework/Versions/1.5/Home" ]; then
    export JAVA_HOME="/System/Library/Frameworks/JavaVM.framework/Versions/1.5/Home"
  else
    echo "Neither the JAVA_HOME nor the JRE_HOME environment variable is defined"
    echo "At least one of these environment variable is needed to run this program"
    exit 1
  fi
fi
if [ -z "$JAVA_HOME" -a "$1" = "debug" ]; then
  echo "JAVA_HOME should point to a JDK in order to run in debug mode."
  exit 1
fi
if [ -z "$JRE_HOME" ]; then
  JRE_HOME="$JAVA_HOME"
fi

if [ "$os400" = "true" ]; then
  if [ ! -x "$JAVA_HOME"/bin/java -o ! -x "$JAVA_HOME"/bin/javac ]; then
    echo "The JAVA_HOME environment variable is not defined correctly"
    echo "This environment variable is needed to run this program"
    echo "NB: JAVA_HOME should point to a JDK not a JRE"
    exit 1
  fi
else
  if [ ! -x "$JAVA_HOME"/bin/java -o ! -x "$JAVA_HOME"/bin/jdb -o ! -x "$JAVA_HOME"/bin/javac ]; then
    echo "The JAVA_HOME environment variable is not defined correctly"
    echo "This environment variable is needed to run this program"
    echo "NB: JAVA_HOME should point to a JDK not a JRE"
    exit 1
  fi
fi

# Set standard CLASSPATH
if [ "$1" = "debug" -o "$1" = "javac" ] ; then
  CLASSPATH="$JAVA_HOME"/lib/tools.jar
fi

# OSX hack to CLASSPATH
JIKESPATH=
if [ `uname -s` = "Darwin" ]; then
  OSXHACK="/System/Library/Frameworks/JavaVM.framework/Versions/CurrentJDK/Classes"
  if [ -d "$OSXHACK" ]; then
    for i in "$OSXHACK"/*.jar; do
      JIKESPATH="$JIKESPATH":"$i"
    done
  fi
fi

# Set standard commands for invoking Java.
_RUNJAVA="$JRE_HOME"/bin/java
if [ "$os400" != "true" ]; then
  _RUNJDB="$JAVA_HOME"/bin/jdb
fi
_RUNJAVAC="$JAVA_HOME"/bin/javac

# When no TTY is available, don't output to console
have_tty=0
if [ "`tty`" != "not a tty" ]; then
  have_tty=1
fi

if [ ! -z "$CLASSPATH" ]; then
  CLASSPATH="$CLASSPATH":"$PRGDIR":"$PRGDIR"/sylla.jar
else
  CLASSPATH="$PRGDIR":"$PRGDIR"/sylla.jar
fi
for i in "$PRGDIR"/lib/*.jar; do
  CLASSPATH="$CLASSPATH":"$i"
done

# For Cygwin, switch paths to Windows format before running java
if $cygwin; then
  JAVA_HOME=`cygpath --absolute --windows "$JAVA_HOME"`
  CLASSPATH=`cygpath --path --windows "$CLASSPATH"`
fi

JAVA_OPTS="-Dfile.encoding=UTF8"
MAINCLASS=com.sylla.Niffler

# only output this if we have a TTY
if [ $have_tty -eq 1 ]; then
  echo "Using JAVA:        $_RUNJAVA"
  echo "Using JAVA_OPTS:   $JAVA_OPTS"
  echo "Using CLASSPATH:   $CLASSPATH"
  echo "Using MAINCLASS:   $MAINCLASS"
fi
exec "$_RUNJAVAW" $JAVA_OPTS -classpath $CLASSPATH $MAINCLASS "$@"