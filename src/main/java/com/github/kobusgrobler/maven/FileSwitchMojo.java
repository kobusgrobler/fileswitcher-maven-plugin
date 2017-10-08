/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.kobusgrobler.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * A utility Maven plugin with a goal that swaps files.
 *
 * Typically used to swap logger config files between production and release builds.
 *
 * @author kobus grobler
 */
@Mojo( name = "fileswitch", defaultPhase = LifecyclePhase.VALIDATE )
public class FileSwitchMojo
    extends AbstractMojo
{
    @Parameter( property = "useDebug", required = false )
    private boolean useDebug;

    @Parameter( property = "outFile", required = true )
    private File outFile;

    @Parameter( property = "debugFile", required = true )
    private File debugFile;

    @Parameter( property = "releaseFile", required = true )
    private File releaseFile;

    @Parameter( defaultValue = "${project}", readonly = true )
    private MavenProject project;

    public void execute()
        throws MojoExecutionException
    {
      Log log = getLog();
        try
        {
            if (releaseFile.exists()) {
                if (useDebug) {
                    log.info("Copying " + debugFile + " to " + outFile);
                    Files.copy(debugFile.toPath(), outFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } else {
                    log.info("Copying " + releaseFile + " to " + outFile);
                    Files.copy(releaseFile.toPath(), outFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            } else {
                throw new MojoExecutionException( "Release file does not exist: " + outFile);
            }
        }
        catch ( IOException e )
        {
            throw new MojoExecutionException( "Error copying file", e );
        }
    }
}
