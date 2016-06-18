MachinaCraft
============

MachinaCraft is a framework for machines made up out of blocks, activated by
right-clicking on a lever. On its own it does nothing, but other plugins can use
it to let players build a Machina that they can activate.

General information about the plugin, comments page and issue tracker can be
found at the [BukkitDev project page][project].

[project]: http://dev.bukkit.org/server-mods/machinacraft

Building
------------------

```
git clone https://github.com/Sycholic/ToolworxReborn.git && mvn package
```

You will find the plugin in the /target folder.

Building the Plugins
--------------------
A rudimentary ant build system is included that can take care of packaging the
plugins into jarfiles. In the MachinaCraft project, rightclick on build.xml and
select Run As -> Ant Build (without the ...)

The jar files will appear in a 'jar' folder under the MachinaCraft root folder.
Advanced: Create a new builder for the MachinaCraft project under properties so
that you can just select the project and in the top menu choose Project ->
 Build Project.

Creating a new Machina Plugin
-----------------------------
When you've created a new java plugin project, make sure you add MachinaCore to
the build path. For an example on how to make a working Machina plugin you can
look at the source code for the existing plugins.
