#!/bin/sh
DIR="$(cd "$(dirname "$0")" && pwd)"
MAVEN_VERSION="3.9.9"
MAVEN_DIR="$HOME/.m2/wrapper/dists/apache-maven-$MAVEN_VERSION"

if [ -x "$MAVEN_DIR/apache-maven-$MAVEN_VERSION/bin/mvn" ]; then
    exec "$MAVEN_DIR/apache-maven-$MAVEN_VERSION/bin/mvn" "$@"
fi

if command -v mvn >/dev/null 2>&1; then
    exec mvn "$@"
fi

echo "Downloading Apache Maven $MAVEN_VERSION..."
mkdir -p "$MAVEN_DIR"
TMP_ZIP="/tmp/apache-maven.zip"
URL="https://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.zip"

if command -v curl >/dev/null 2>&1; then
    curl -fsSL -o "$TMP_ZIP" "$URL"
elif command -v wget >/dev/null 2>&1; then
    wget -qO "$TMP_ZIP" "$URL"
else
    echo "Neither curl nor wget found. Please install Apache Maven manually."
    exit 1
fi

unzip -q -o "$TMP_ZIP" -d "$MAVEN_DIR"
rm -f "$TMP_ZIP"
chmod +x "$MAVEN_DIR/apache-maven-$MAVEN_VERSION/bin/mvn"

exec "$MAVEN_DIR/apache-maven-$MAVEN_VERSION/bin/mvn" "$@"

