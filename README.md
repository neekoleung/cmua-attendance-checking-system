# Attendance check-in system
__*A biometric user identification and information system for CMUA students.*__
(Group project for _95-712 OOP in Java_ of CMU)

## Basic Features
For visitors:
* __Shoot and facial recogniation__: the process is triggered automatically.

For receptionist:
* __Information presentation on the dashboard__: photo and basic details of the student, last visit date, times visited, reasons for visits, general information and announcements _etc._. 
* __Information input option__: basic infomation of new visitors, visiting reason of students _etc._.

For admin staff:
* __Analytic reports generation__:
__a__. Report on students visiting reception by categories for a given date range; 
__b__. Report on the frequency of visits to the reception by students grouped by gender and categories for a specific date range.

## Data Flow
![](https://res.cloudinary.com/dvrxfispp/image/upload/v1583328726/Github/cmu-facial/cmu-flowchart_tnvena.png)

## External Libraries
- [Sarxos](https://oss.sonatype.org/service/local/artifact/maven/redirect?r=snapshots&g=com.github.sarxos&a=webcam-capture&v=0.3.13-SNAPSHOT): Webcam Capture API
- [image-processing](https://jar-download.com/artifacts/org.openimaj) from __*org.openimaj*__ (version 1.3.9)
- [object-detection](https://jar-download.com/?search_box=org.openimaj&p=11) from __*org.openimaj*__ (version 1.3.9)
- [faces](https://jar-download.com/?search_box=org.openimaj&p=10) from __*org.openimaj*__ (version 1.3.9)
- [Emotion detection API](https://cloud.google.com/vision/docs/drag-and-drop) from __*Google Cloud*__
- [Java Advanced Imaging Image I/O Tools API core](https://bintray.com/jai-imageio/maven/download_file?file_path=com%2Fgithub%2Fjai-imageio%2Fjai-imageio-core%2F1.4.0%2Fjai-imageio-core-1.4.0.jar)
