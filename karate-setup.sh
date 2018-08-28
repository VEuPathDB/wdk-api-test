#! /bin/sh

echo "Setting Up Karate"
proRoot="$( cd "$(dirname "$0")" ; pwd -P )"
tmpJson="/tmp/$( uuidgen ).json"

curl -so "$tmpJson" https://api.github.com/repos/intuit/karate/releases/latest

download=`grep browser_download_url $tmpJson | cut -d '"' -f 4`
jarFile=`basename "$download"`

echo "Downloading version \"$( grep tag_name $tmpJson | cut -d '"' -f 4 )\""
curl --output "$jarFile" --progress-bar --location "$download"

if [ $? -eq 0 ]; then
  echo "Installing"
  cat "$proRoot/misc/exec-prefix.tpl.sh" "$jarFile" > "karate" \
    && chmod +x "karate" \
    && sudo mv -t "/usr/local/bin" "karate"
else
  echo "Download failed"
fi

echo "Performing cleanup"
rm -rf "$tmpJson" "$jarFile"
