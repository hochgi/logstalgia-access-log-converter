# logstalgia-access-log-converter
Converter of [CM-Well](https://github.com/thomsonreuters/CM-Well) access.log files into a [logstalgia](http://logstalgia.io) acceptable format

## Building
The project uses [mill](http://www.lihaoyi.com/mill) build tool.  
To build, simply run:
```sh
…/logstalgia-access-log-converter$ mill logconv.assembly
```
Which will create a runnable jar at: `out/logconv/assembly/dest/out.jar`

## Running
```sh
java -jar out/logconv/assembly/dest/out.jar --input /path/to/cm-well/access.log
```

### Hashing IP addresses
By default, CM-Well's access.log expose client IP addresses making the request.  
Since logstalgia videos are probably meant to be shared publicly, you might want to not show real users IPs.
You can hash (using [xxhash](https://cyan4973.github.io/xxHash)) the original IPs and disguise 'em, 
by providing `--hash-ip` parameter. You may also use a custom seed for the hash by also providing `--hash-seed` parameter.

### Help
by providing `--help` you'll get the following output:
```sh
…/logstalgia-access-log-converter$ java -jar out/logconv/assembly/dest/out.jar --help
Options
Usage: options [options]
  --usage  <bool>
        Print usage and exit
  --help | -h  <bool>
        Print help message and exit
  --input  <string>
  --hash-ip  <bool>
  --hash-seed  <int?>

```
