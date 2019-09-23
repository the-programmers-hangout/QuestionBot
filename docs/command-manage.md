# Management Commands

These commands are used to configure the bot.

## Table of Contents

- [AddAnswer](#addanswer)
- [DelAnswer](#delanswer)
- [EnableLogging](#enablelogging)
- [SetChannel](#setchannel)
- [SetPrefix](#setprefix)
- [SetRole](#setrole)

## AddAnswer (Admin Only)

Adds an answer from a user for the given question

**Syntax**

```
$addanswer <question> <user> <answer>
```              

**Arguments**      

- `question`: The question id to answer to
- `user`: The user snowflake to answer for
- `answer`: The text answer 

## DelAnswer (Admin Only)
    
**Syntax**

Deletes the answer from given user for given question

```
$delanswer <question> <user>
```                

**Arguments**  

- `question`: The question id to delete an answer from
- `user`: The user snowflake to delete answer from

## EnableLogging (Admin Only)

Toggles logging command invocations.

**Example**

![Example](/.github/assets/commands/logging-example.png)

**Syntax**

```
$enablelogging <setting>
```

**Arguments**  

-  `<setting>`: Either `on` or `off`.
    - `on`: Turns on logging
    - `off`: Turns off logging

## SetChannel (Admin Only)

Sets the channel to emit `<target>` messages to.

**Example**

![Example](/.github/assets/commands/setchannel-example.png)

**Syntax**

```
$setchannel <target> <channel>
```

**Arguments**  

- `<target>`: The category. Either `log`, `questions` or `answers`
    - `log`: The text channel to emit logs to
    - `questions`: The text channel to emit questions to
    - `answers`: The text channel to emit answers to
- `channel`: The text channel to bind these events to

## SetPrefix (Admin Only)

Changes the prefix used to invoke commands.

Note: This setting modifies the bot prefix for every guild it is in. Use with caution.

**Example**

![Example](/.github/assets/commands/setprefix-example.png)

**Syntax**

```
$setprefix <prefix>
```

**Arguments**

- `prefix`: The new bot prefix.  

## SetRole (Admin Only)

Sets the minimum required role to invoke bot commands.

**Example**

![Example](/.github/assets/commands/setrole-example.png)

**Syntax**

```
$setrole <role>
```

**Arguments**  

- `role`: The name of the new role (not snowflake)
