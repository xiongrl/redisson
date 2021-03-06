/**
 * Copyright 2014 Nikita Koksharov, Nickolay Borbit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.redisson;

import java.util.UUID;
import java.util.concurrent.locks.Lock;

import org.redisson.command.CommandExecutor;
import org.redisson.core.RLock;
import org.redisson.core.RReadWriteLock;

/**
 * A {@code ReadWriteLock} maintains a pair of associated {@link
 * Lock locks}, one for read-only operations and one for writing.
 * The {@link #readLock read lock} may be held simultaneously by
 * multiple reader threads, so long as there are no writers.  The
 * {@link #writeLock write lock} is exclusive.
 *
 * Works in non-fair mode. Therefore order of read and write
 * locking is unspecified.
 *
 * @author Nikita Koksharov
 *
 */
public class RedissonReadWriteLock extends RedissonExpirable implements RReadWriteLock {

    private final UUID id;
    private final CommandExecutor commandExecutor;

    RedissonReadWriteLock(CommandExecutor commandExecutor, String name, UUID id) {
        super(commandExecutor, name);
        this.commandExecutor = commandExecutor;
        this.id = id;
    }

    @Override
    public RLock readLock() {
        return new RedissonReadLock(commandExecutor, getName(), id);
    }

    @Override
    public RLock writeLock() {
        return new RedissonWriteLock(commandExecutor, getName(), id);
    }

}
