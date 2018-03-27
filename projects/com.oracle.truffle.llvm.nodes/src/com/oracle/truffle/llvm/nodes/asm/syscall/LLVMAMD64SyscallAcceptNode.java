/*
 * Copyright (c) 2017, Oracle and/or its affiliates.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of
 * conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided
 * with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS
 * OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.oracle.truffle.llvm.nodes.asm.syscall;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.llvm.nodes.asm.syscall.posix.LLVMAMD64PosixCallNode;
import com.oracle.truffle.llvm.nodes.asm.syscall.posix.LLVMAMD64PosixCallNodeGen;
import com.oracle.truffle.llvm.runtime.LLVMAddress;
import com.oracle.truffle.llvm.runtime.memory.LLVMSyscallOperationNode;

public abstract class LLVMAMD64SyscallAcceptNode extends LLVMSyscallOperationNode {
    @Child private LLVMAMD64PosixCallNode accept;

    public LLVMAMD64SyscallAcceptNode() {
        accept = LLVMAMD64PosixCallNodeGen.create("accept", "(SINT32,UINT64,UINT64):SINT32", 3);
    }

    @Override
    public final String getName() {
        return "accept";
    }

    @Specialization
    protected long doOp(long sockfd, LLVMAddress addr, LLVMAddress addrlen) {
        return (int) accept.execute((int) sockfd, addr.getVal(), addrlen.getVal());
    }

    @Specialization
    protected long doOp(long sockfd, long addr, long addrlen) {
        return doOp(sockfd, LLVMAddress.fromLong(addr), LLVMAddress.fromLong(addrlen));
    }
}
