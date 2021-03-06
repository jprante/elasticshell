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
package org.elasticsearch.shell;

import jline.console.completer.Completer;
import jline.console.completer.CompletionHandler;
import org.elasticsearch.common.inject.AbstractModule;
import org.elasticsearch.common.inject.TypeLiteral;
import org.elasticsearch.common.inject.name.Names;
import org.elasticsearch.shell.client.ClientFactory;
import org.elasticsearch.shell.client.RhinoClientFactory;
import org.elasticsearch.shell.client.RhinoClientNativeJavaObject;
import org.elasticsearch.shell.console.Console;
import org.elasticsearch.shell.console.JLineConsole;
import org.elasticsearch.shell.console.completer.JLineCompletionHandler;
import org.elasticsearch.shell.console.completer.JLineRhinoCompleter;
import org.elasticsearch.shell.json.JsonToString;
import org.elasticsearch.shell.json.RhinoJsonToString;
import org.elasticsearch.shell.json.RhinoStringToJson;
import org.elasticsearch.shell.json.StringToJson;
import org.elasticsearch.shell.script.RhinoScriptExecutor;
import org.elasticsearch.shell.script.ScriptExecutor;
import org.elasticsearch.shell.source.InputAnalyzer;
import org.elasticsearch.shell.source.RhinoInputAnalyzer;
import org.mozilla.javascript.NativeObject;

import java.io.InputStream;
import java.io.PrintStream;

/**
 * Module that binds all the objects needed to run the shell
 *
 * @author Luca Cavanna
 */
public class ShellModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(String.class).annotatedWith(Names.named("appName")).toInstance("elasticshell");
        bind(InputStream.class).annotatedWith(Names.named("shellInput")).toInstance(System.in);
        bind(PrintStream.class).annotatedWith(Names.named("shellOutput")).toInstance(new PrintStream(System.out, true));
        bind(ShutdownHook.class).asEagerSingleton();

        //JLine bindings
        bind(new TypeLiteral<Console<PrintStream>>(){}).to(JLineConsole.class).asEagerSingleton();
        bind(Completer.class).to(JLineRhinoCompleter.class).asEagerSingleton();
        bind(CompletionHandler.class).to(JLineCompletionHandler.class).asEagerSingleton();

        //Rhino bindings
        bind(new TypeLiteral<JsonToString<NativeObject>>(){}).to(RhinoJsonToString.class).asEagerSingleton();
        bind(new TypeLiteral<StringToJson<Object>>(){}).to(RhinoStringToJson.class).asEagerSingleton();
        bind(Unwrapper.class).to(RhinoUnwrapper.class).asEagerSingleton();
        bind(new TypeLiteral<ShellScope<RhinoShellTopLevel>>(){}).to(RhinoShellScope.class).asEagerSingleton();
        bind(ScriptExecutor.class).to(RhinoScriptExecutor.class).asEagerSingleton();
        bind(InputAnalyzer.class).to(RhinoInputAnalyzer.class).asEagerSingleton();
        bind(Shell.class).to(RhinoShell.class).asEagerSingleton();

        bind(new TypeLiteral<ClientFactory<RhinoClientNativeJavaObject>>() {}).to(RhinoClientFactory.class);
    }
}
