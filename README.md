IRP 1608
=======

The IRP 1608 is a simple 16 bit processor. It is often regarded as the
reason Steve lost the moon race during the cold war.

IRP 1608 is a 16 bit, 8 tick processor with an instruction set of 16
instructions in total.

| Instruction | OP code |    Data     |       Description       |
| ----------- | ------- | ----------- | ----------------------- |
| Jump        | 0x0     | A = 0x0FFF  | Jumps to the designated |
|             |         |             | address [ 0:12 ]        |
| :---------: | :-----: | :---------: | :---------------------: |
| Jump and    | 0x1     | A = 0x0FFF  | Jumps to the designated |
| link        |         |             | address [ 0:12 ] and    |
|             |         |             | stores the jumper in $1 |
| :---------: | :-----: | :---------: | :---------------------: |
| Jump        | 0x2     | rB = 0x00F0 | Jumps to the address    |
| register    |         |             | stored in register `rB` |
| :---------: | :-----: | :---------: | :---------------------: |
| Add         | 0x3     | rR = 0x0F00 | Adds two integers       |
|             |         | rA = 0x00F0 | together (unsigned)     |
|             |         | rB = 0x000F | and stores the result   |
|             |         |             | in register `rR`        |
| :---------: | :-----: | :---------: | :---------------------: |


inst[0x4] = this::subtract;
inst[0x5] = this::multiply;
inst[0x6] = this::divide;

inst[0x7] = this::leftShift;
inst[0x8] = this::rightShift;
inst[0x9] = this::nand;

inst[0xA] = this::branchLessThan;
inst[0xB] = this::invokeSystemCall;

inst[0xC] = this::loadImmediate;
inst[0xD] = this::loadByte;
inst[0xE] = this::storeByte;

inst[0xF] = this::nop;

Functions are stored as `0x0 - 0xFF` and may be invoked
as pleased by the programmer.

The first `0x0 - 0xF` are built in processor functions and can
not be modified.

| CSR |   Code   | Parameters |
|-----|----------|------------|
| 0x0 | Halt     |            |
| 0x1 | Error    | a0, error  |


All modifiable traps are stored at `0x10` and above. To call them
add `0x10` to their codes.

| CSR (+0x10) |  Code (*Error)   |
|-------------|------------------|
|    0x0      | *NULL            |
|    0x1      | *STACK OVERFLOW  |
|    0xE      | *Invalid Address |
|    0xF      | *Func not found  |
|:-----------:|:----------------:|



Run
=======
`./gradlew runClient`



Installation information
=======

This template repository can be directly cloned to get you started with a new
mod. Simply create a new repository cloned from this one, by following the
instructions provided by [GitHub](https://docs.github.com/en/repositories/creating-and-managing-repositories/creating-a-repository-from-a-template).

Once you have your clone, simply open the repository in the IDE of your choice. The usual recommendation for an IDE is either IntelliJ IDEA or Eclipse.

If at any point you are missing libraries in your IDE, or you've run into problems you can
run `gradlew --refresh-dependencies` to refresh the local cache. `gradlew clean` to reset everything
{this does not affect your code} and then start the process again.

Mapping Names:
============
By default, the MDK is configured to use the official mapping names from Mojang for methods and fields
in the Minecraft codebase. These names are covered by a specific license. All modders should be aware of this
license. For the latest license text, refer to the mapping file itself, or the reference copy here:
https://github.com/NeoForged/NeoForm/blob/main/Mojang.md

Additional Resources:
==========
Community Documentation: https://docs.neoforged.net/
NeoForged Discord: https://discord.neoforged.net/
