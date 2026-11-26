IRP 1608
=======

The IRP 1608 is a simple 16 bit processor. It is often regarded as the
reason Steve lost the moon race during the cold war.

IRP 1608 is a 16 bit, 8 tick processor with an instruction set of 16
instructions in total. All arithmetic operations are unsigned and all
data is treated as unsigned by the processor.

| Instruction | OP code |    Data     |       Description       |
| ----------- | ------- | ----------- | ----------------------- |
| Jump        | 0x0     | A  = 0x0FFF | Jumps to the designated |
|             |         |             | address [ 0:12 ]        |
| :---------: | :-----: | :---------: | :---------------------: |
| Jump and    | 0x1     | A  = 0x0FFF | Jumps to the designated |
| link        |         |             | address [ 0:12 ] and    |
|             |         |             | stores the jumper in $1 |
| :---------: | :-----: | :---------: | :---------------------: |
| Jump        | 0x2     | rA = 0x00F0 | Jumps to the address    |
| register    |         |             | stored in register `rA` |
| :---------: | :-----: | :---------: | :---------------------: |
| Add         | 0x3     | rR = 0x0F00 | Adds `rA` with `rB`     |
|             |         | rA = 0x00F0 | together and stores     |
|             |         | rB = 0x000F | the result in `rR`      |
| :---------: | :-----: | :---------: | :---------------------: |
| Subtract    | 0x4     | rR = 0x0F00 | Subtracts `rB` from     |
|             |         | rA = 0x00F0 | `rA` and stores the     |
|             |         | rB = 0x000F | result in `rR`          |
| :---------: | :-----: | :---------: | :---------------------: |
| Multiply    | 0x5     | rR = 0x0F00 | Multiplies `rA` with    |
|             |         | rA = 0x00F0 | `rB` and stores the     |
|             |         | rB = 0x000F | result in `rR`          |
| :---------: | :-----: | :---------: | :---------------------: |
| Divide      | 0x6     | rR = 0x0F00 | Divides `rA` with `rB`  |
|             |         | rA = 0x00F0 | and the result is       |
|             |         | rB = 0x000F | stored in `rR`          |
| :---------: | :-----: | :---------: | :---------------------: |
| Left shift  | 0x7     | rR = 0x0F00 | Shifts `A` bits in `rA` |
|             |         | rA = 0x00F0 | left and stores it in   |
|             |         | A  = 0x000F | the register `rR`       |
| :---------: | :-----: | :---------: | :---------------------: |
| Right shift | 0x8     | rR = 0x0F00 | Shifts `A` bits in `rA` |
|             |         | rA = 0x00F0 | right and stores it in  |
|             |         | A  = 0x000F | the register `rR`       |
| :---------: | :-----: | :---------: | :---------------------: |
| NAND        | 0x9     | rR = 0x0F00 | Preforms NAND on `rA`   |
|             |         | rA = 0x00F0 | and `rB` and stores the |
|             |         | rB = 0x000F | result in registry `rR` |
| :---------: | :-----: | :---------: | :---------------------: |
| Branch less | 0xA     | rR = 0x0F00 | If `rA` is less than    |
| than        |         | rA = 0x00F0 | `rB` this will jump to  |
|             |         | rB = 0x000F | the address in `rR`     |
| :---------: | :-----: | :---------: | :---------------------: |
| System call | 0xB     |             | Preforms a system call  |
|             |         |             | to the function in `v0` |
| :---------: | :-----: | :---------: | :---------------------: |
| Load        | 0xC     | rR = 0x0F00 | Loads the immediate     |
| immediate   |         | im = 0x00FF | `im` into `rR`          |
| :---------: | :-----: | :---------: | :---------------------: |
| Load byte   | 0xD     | rR = 0x0F00 | Loads byte into `rR`    |
|             |         | rA = 0x00F0 | from the address `rA`   |
|             |         | of = 0x000F | with offset `of`        |
| :---------: | :-----: | :---------: | :---------------------: |
| Store byte  | 0xE     | rR = 0x0F00 | Stores the byte in `rR` |
|             |         | rA = 0x00F0 | lower half at the       |
|             |         | of = 0x000F | address in `rA` with an |
|             |         |             | offset of `of`          |
| :---------: | :-----: | :---------: | :---------------------: |
| No          | 0xF     |             | Does nothing,           |
| operation   |         |             | and increases PPC       |
| :---------: | :-----: | :---------: | :---------------------: |



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
| 0x10 - 0xEF | Custom functions |
|:-----------:|:----------------:|


Registers are split up into different types. Some are used by the processor
and are therefore only available to the processor while others are considered
public registers. Such registers are the *GPR* or *general purpose registers*.

| Register ( GPR ) |    Description    |
| ---------------- | ----------------- |
| $0, $zero        | Always `0`        |
|:----------------:|:-----------------:|
| $1, $ra          | Return address    |
|:----------------:|:-----------------:|
| $2-$5, $t0-$t4   | Temporary         |
|:----------------:|:-----------------:|
| $6-$9, $s0-$s4   | These contains    |
|                  | the same value    |
|                  | before and after  |
|                  | a subroutine call |
|:----------------:|:-----------------:|
| $10, $v0         | Return value      |
|:----------------:|:-----------------:|
| $11-$13, $a0-$a2 | Argument 0-2      |
|:----------------:|:-----------------:|
| $14, $fp         | Extra, may be     |
|                  | used for graphics |
|:----------------:|:-----------------:|
| $15, $sp         | Stack pointer     |
|:----------------:|:-----------------:|


A simple program that loops five times and exits without any error
by simply calling `halt`

```asm
0xC305  // li $t1 0x5
0xC200  // li $t0 0x0
0xC60A  // li $s0 0xA
0xA623  // blt $s0 $t0 $t1

0x0010  // jmp 0x010        % Jumps to `0xCA00` or to address `16` in RAM

0xC401  // li $t2 0x1
0x3224  // add $t0 $t0 $t2
0x0006  // jmp 0x004        % Jumps to `0xC60A` or to address `4` in RAM

0xCA00  // li $v0 0x0       % Halts ( Exit without any error )
0xB000  // sys
```



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
