#!/bin/bash
set -ev
if [ "$TRAVIS_JDK_VERSION" == "oraclejdk7" ] && [ "$TRAVIS_PULL_REQUEST" == "false" ] && [ "$TRAVIS_BRANCH" == "master" ]; then

if [[ -z "$GH_TOKEN" ]]; then
echo -e "GH_TOKEN is not set"
exit 1
fi

if [ ! -f $TRAVIS_BUILD_DIR/target/gwt-material-template-*.war ]; then
echo -e "starter war file not found."
exit 1
fi

echo -e "Publishing starter to gh-pages . . .\n"

git config --global user.email "travis@travis-ci.org"
git config --global user.name "travis-ci"

# clone the gh-pages branch.
cd $HOME
rm -rf gh-pages
git clone --quiet --branch=gh-pages https://$GH_TOKEN@github.com/GwtMaterialDesign/gwt-material-template gh-pages > /dev/null
cd gh-pages

# remove the GwtMaterialStarter directories from git.
if [[ -d ./GWTMaterialStarter ]]; then
git rm -rf ./GWTMaterialStarter
fi
if [[ -f ./index.html ]]; then
git rm -rf ./index.html
fi
if [[ -d ./META-INF ]]; then
git rm -rf ./META-INF
fi
if [[ -d ./WEB-INF ]]; then
git rm -rf ./WEB-INF
fi

# copy the new GwtMaterialStarter the snapshot dir.
unzip -u $TRAVIS_BUILD_DIR/target/gwt-material-template-*.war -d ./
rm -rf ./META-INF
rm -rf ./WEB-INF

git add -f .
git commit -m "Auto-push demo to gh-pages successful. (Travis build: $TRAVIS_BUILD_NUMBER)"
git push -fq origin gh-pages > /dev/null

echo -e "Published starter to gh-pages.\n"

fi
