
## generate test protobuf file

    wget -P ~/bin  https://maven.aliyun.com/repository/public/com/google/protobuf/protoc/4.28.2/protoc-4.28.2-linux-x86_64.exe
    chmod +x ~/bin/protoc-4.28.2-linux-x86_64.exe
    ~/bin/protoc-4.28.2-linux-x86_64.exe  --java_out=src/test/scala -Isrc/test/resources src/test/resources/account.proto
