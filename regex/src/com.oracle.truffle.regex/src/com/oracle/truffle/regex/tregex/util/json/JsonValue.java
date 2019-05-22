/*
 * Copyright (c) 2018, 2018, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package com.oracle.truffle.regex.tregex.util.json;

import com.oracle.truffle.api.TruffleFile;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.StandardOpenOption;

public abstract class JsonValue implements JsonConvertible {

    @Override
    public JsonValue toJson() {
        return this;
    }

    public void dump(TruffleFile path) {
        try (PrintWriter writer = new PrintWriter(path.newBufferedWriter(StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING))) {
            dump(writer, 0);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract void dump(PrintWriter writer, int indent);

    static void printIndent(PrintWriter writer, int indent) {
        for (int i = 0; i < indent; i++) {
            writer.print(' ');
        }
    }

    @Override
    public String toString() {
        StringWriter stringWriter = new StringWriter();
        dump(new PrintWriter(stringWriter), 0);
        return stringWriter.toString();
    }
}
