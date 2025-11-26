# CIP4 PrintTalk Java Library
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.cip4.lib.ptk/PrintTalkJ/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.cip4.lib.jdf/JDFLibJ) [![License (CIP4 Software License)](https://img.shields.io/badge/license-CIP4%20Software%20License-blue)](https://github.com/cip4/PrintTalkJ/blob/master/LICENSE.md)

CIP4 PrintTalk Library

## Issue Tracking
Don't write issues, provide Pull-Requests!

## Development Notes
### Release a new Version
Creation and publishing of a new version to GitHub Release and to the Central Maven Repository. 

**NOTE:** The publication to the Central Maven Repository may take up to two hours.

```bash
$ git tag -a PrintTalkJ-2.1.[VERSION] -m "[TITLE]"
$ git push origin PrintTalkJ-2.1.[VERSION]
```

In case a build has been failed, a tag can be deleted using the following command:
```bash
$ git tag -d PrintTalkJ-2.1.[VERSION]
$ git push origin :refs/tags/PrintTalkJ-2.1.[VERSION]
```
