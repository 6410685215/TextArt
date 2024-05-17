# CN311 OS

## convert Image to TextArt
- convert image to text art using TCP/IP
- server will convert image to text art and send it to client
- client will receive text art and save it to file

## Example 1
### How to run
terminal 1
```bash 
$ java ./bin/App server
```
terminal 2
```bash
$ java ./bin/App client <path_to_image>
```
open file `output.txt` to see the result