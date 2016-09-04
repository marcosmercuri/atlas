#!/bin/bash

if [ "$#" -ne 1 ]
then
  echo "Provide twitter username"
  exit 1
fi

read -s -p "Enter twitter Password: " password

#"Look away. I'm hideous" (https://www.youtube.com/watch?v=Ka1PeNNi6dg)

login=$(curl 'http://localhost:8080/signin/twitter' -H 'Connection: keep-alive' --data '' -i 2>/dev/null)
echo ""
echo "signin via twitter"

xAuthToken=$(sed -n -e 's/^.*X-auth-token: //Ip'  <<< "$login" | tr -d '\012\015')

urlToCall=$(sed -n -e 's/^.*Location: //Ip' <<< "$login" | tr -d '\012\015')

twitter=$(curl $urlToCall -H 'upgrade-insecure-requests: 1' -H 'cache-control: no-cache' -H 'authority: api.twitter.com' -H 'referer: http://localhost:8080/' --compressed -i 2>/dev/null)
echo "Calling twitter to login"

twitter_sess=$(sed -n -e 's/^.*_twitter_sess=//p' <<< "$twitter" | tr -d '\012\015' | sed -n -e 's/; P.*$//p')

authenticity_token=$(sed -n -e 's/^.*<input name=\"authenticity_token\" type=\"hidden\" value=\"//p' <<< "$twitter" | tr -d '\012\015' | sed -n -e 's/\">$//p')

redirect_after_login=$(sed -n -e 's/^.*<input name=\"redirect_after_login\" type=\"hidden\" value=\"//p' <<< "$twitter" | tr -d '\012\015' | sed -n -e 's/\">$//p')

oauth_token=$(sed -n -e 's/^.*name=\"oauth_token\" type=\"hidden\" value=\"//p' <<< "$twitter" | tr -d '\012\015' | sed -n -e 's/\">$//p')

authentication=$(curl 'https://api.twitter.com/oauth/authenticate' -H 'cookie: _twitter_sess='$twitter_sess'' -H 'upgrade-insecure-requests: 1' -H 'cache-control: no-cache' -H 'authority: api.twitter.com' -H 'origin: https://api.twitter.com'  --data 'authenticity_token='$authenticity_token'&redirect_after_login='$redirect_after_login'&oauth_token='$oauth_token'&session%5Busername_or_email%5D='$1'&session%5Bpassword%5D='$password'' --compressed -i 2>/dev/null)
echo "Logging into twitter"

oauthVerifier=$(sed -n -e 's/^.*localhost\:8080\/signin\/twitter.*\&oauth_verifier\=//Ip' <<< "$authentication" | sed -n -e 's/\">$//p' | tr -d '\012\015')

logged=$(curl 'http://localhost:8080/signin/twitter?oauth_token='$oauth_token'&oauth_verifier='$oauthVerifier'' -H 'Pragma: no-cache' -H 'Accept-Encoding: gzip, deflate, sdch' -H 'Accept-Language: es,en-US;q=0.8,en;q=0.6' -H 'Upgrade-Insecure-Requests: 1' -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8' -H 'Cache-Control: no-cache' -H 'x-auth-token:'$xAuthToken'' -H 'Connection: keep-alive' --compressed -i 2>/dev/null)
echo "Redirecting to app"

redirect=$(sed -n -e 's/^.*location: //Ip' <<< "$logged" | tr -d '\012\015')

signup=$(curl ''$redirect'' -H 'Pragma: no-cache' -H 'Accept-Encoding: gzip, deflate, sdch' -H 'Accept-Language: es,en-US;q=0.8,en;q=0.6' -H 'Accept: */*' -H 'x-auth-token: '$xAuthToken'' -H 'Connection: keep-alive' -H 'Cache-Control: no-cache' --compressed 2>/dev/null)

username=$(curl 'http://localhost:8080/api/session' -H 'Pragma: no-cache' -H 'Accept-Encoding: gzip, deflate, sdch' -H 'Accept-Language: es,en-US;q=0.8,en;q=0.6' -H 'Accept: */*' -H 'x-auth-token: '$xAuthToken'' -H 'Connection: keep-alive' -H 'Cache-Control: no-cache' --compressed 2>/dev/null)

echo "Here you should see your user info:" "$username"
echo "On further calls use this header: x-auth-token: $xAuthToken"
