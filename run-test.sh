#! /bin/bash

email="$EMAIL"
password="$PASSWORD"
sitePath="$SITE_PATH"
authType="OAUTH"

function printHelp {
  echo "Usage:

  run [-l][ -e <email address>][ -p <password>][ -u <site url>][ -- gradle args...]

Arguments:

  [-e <email address>]    Default site login email address.  If this value is
                          not provided it will be requested interactively.

  [-p <password>]         Default site login password.  If this value is not
                          provided it will be requested interactively.

  [-u <site url>]         URL of the site which will have it's API tested.  If
                          this value is not provided it will be requested
                          interactively.

  -h                      Show this help text

  -l                      Use legacy login scheme for authentication instead of
                          OAuth.

Additional Params:

  [gradle args...]        Additional arguments/parameters that will be passed
                          to gradle.

Examples

Run in interactive mode (or with env vars) and tell gradle to print stacktraces:
run -- --stacktrace

Run with all run args:
run -l -e some@email.addr -p abc123 -u http://site.com/thing
"
}

invalid=0

while getopts "e:p:u:hl" opt; do
  case $opt in
    h)
      printHelp
      exit 0
      ;;
    e)
      email="$OPTARG"
      ;;
    p)
      password="$OPTARG"
      ;;
    u)
      sitePath="$OPTARG"
      ;;
    l)
      authType="LEGACY"
      ;;
    \?)
      invalid=1
      ;;
  esac
done

shift "$(expr $OPTIND - 1)"

if [ $invalid -eq 1 ]; then
  echo
  printHelp
  exit 1
fi

if [[ -z "$email" ]]; then
  echo -n "Username/Email: "
  read email
  echo
fi

if [[ -z "$password" ]]; then
  echo -n "Password: "
  read -s password
  echo
fi

if [[ -z "$sitePath" ]]; then
  echo -n "Base site (eg http://plasmodb.org/plasmo): "
  read sitePath
  echo
fi

EMAIL="${email:?}" \
  PASSWORD="${password:?}" \
  SITE_PATH="${sitePath:?}" \
  AUTH_TYPE="${authType:?}" \
  ./gradlew test $@
