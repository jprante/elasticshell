/*
 * Licensed to Luca Cavanna (the "Author") under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. Elastic Search licenses this
 * file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.elasticsearch.shell.command;

import org.elasticsearch.shell.MessageHelper;
import org.elasticsearch.shell.console.Console;

import java.io.PrintStream;

/**
 * Base class for commands. Might not be really needed but handy to use anyway
 * rather than referring to generic objects as commands. Probably useful in the future
 * to provide common features to all commands though.
 *
 * @author Luca Cavanna
 */
public abstract class Command {

    protected final Console<PrintStream> console;

    protected Command(Console<PrintStream> console) {
        this.console = console;
    }

    public String help() {
        return MessageHelper.getMessage(getClass().getSimpleName() + "." + "help");
    }
}
