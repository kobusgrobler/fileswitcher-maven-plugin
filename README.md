# fileswitcher-maven-plugin
A Maven plugin to switch files between release and debug versions.

Typically used to switch log configuration files used 
during development with that used for a production build.

### To build, test and install the plugin
```mvn -Prun-its install```

### Usage

To copy logging-debug.properties to logging.properties IF 
the debug variable is set to true, otherwise use logging-release.properties

```xml
            <plugin>
                <groupId>com.github.kobusgrobler</groupId>
                <artifactId>fileswitcher-maven-plugin</artifactId>
                <version>1.0.0</version>
                <configuration>
                    <useDebug>${debug}</useDebug>
                    <outFile>logging.properties</outFile>
                    <debugFile>logging-debug.properties</debugFile>
                    <releaseFile>logging-release.properties</releaseFile>
                </configuration>
                <executions>
                    <execution>
                        <id>fileswitchid</id>
                        <phase>validate</phase>
                        <goals>
                            <goal>fileswitch</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>

```
