IRP 1608
=======

The IRP 1608 is a simple 16 bit processor. It is often regarded as the
reason Steve lost the moon race.

Functions are stored as `0x0 - 0xFF` and may be invoked
as pleased by the programmer.

The first `0x0 - 0xF` are built in processor functions and can
not be modified.

| CSR |   Code   |
|-----|----------|
| 0x0 | Halt     |


All modifiable traps are stored at `0x10` and above.

| CSR (+0x10) | Code (*Error) |
|-------------|---------------|
|    0x0      | *NULL         |
|    0x1      | *



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
