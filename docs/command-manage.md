# Management Commands

These commands are used to configure the bot.

## Table of Contents

- [AddAnswer](#addanswer)
- [DelAnswer](#delanswer)
- [EnableLogging](#enablelogging)
- [SetChannel](#setchannel)
- [SetPrefix](#setprefix)
- [SetRole](#setrole)

## AddAnswer

**Syntax**

```
$addanswer
```

**Arguments**       

## DelAnswer
    
**Syntax**

```
$delanswer
```

**Arguments**  

## EnableLogging

Toggles logging command invocations.

**Syntax**

```
$enablelogging <setting>
```

**Arguments**  

-  `<setting>`: Either `on` or `off`.
    - `on`: Turns on logging
    - `off`: Turns off logging

## SetChannel

Sets the channel to emit `<target>` messages to.

**Syntax**

```
$setchannel <target> <channel>
```

**Arguments**  

- `<target>`: The category. Either `log`, `questions` or `answers`
    - `log`: The text channel to emit logs to
    - `questions`: The text channel to emit questions to
    - `answers`: The text channel to emit answers to

## SetPrefix

Changes the prefix used to invoke commands.

Note: This setting modifies the bot prefix for every guild it is in. Use with caution.

**Syntax**

```
$setprefix <prefix>
```

**Arguments**

- `prefix`: The new bot prefix.  

## SetRole

Sets the minimum required role to invoke bot commands.

**Syntax**

```
$setrole <role>
```

**Arguments**  

- `role`: The name of the new role (not snowflake)
