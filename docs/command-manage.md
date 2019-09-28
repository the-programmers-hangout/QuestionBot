# Management Commands

These commands are used to configure the bot.

## Table of Contents

- [AddAnswer](#addanswer-admin-only)
- [DelAnswer](#delanswer-admin-only)
- [EnableLogging](#enablelogging-admin-only)
- [SetChannel](#setchannel-admin-only)
- [SetPrefix](#setprefix-admin-only)
- [SetRole](#setrole-admin-only)

## AddAnswer (Admin Only)

Adds an answer from a user for the given question

**Example**
![Example](/.github/assets/commands/addanswer-id.png)
![Example](/.github/assets/commands/addanswer-mention.png)
![Example](/.github/assets/commands/addanswer-answer.png)

**Syntax**

```
$addanswer <question> <user> <answer>
```              

**Arguments**      

- `question`: The question id to answer to
- `user`: The user snowflake to answer for
- `answer`: The text answer 

## DelAnswer (Admin Only)

Deletes the answer from given user for given question

**Example**
![Example](/.github/assets/commands/delanswer-id.png)
![Example](/.github/assets/commands/delanswer-mention.png)

**Syntax**

```
$delanswer <question> <user>
```                

**Arguments**  

- `question`: The question id to delete an answer from
- `user`: The user snowflake to delete answer from

## ConvertQuestion (Admin Only)

Convert a given message in a given channel to a new QuestionBot question.

**Example**
![Example](/.github/assets/commands/convertquestion-example-1.PNG)
![Example](/.github/assets/commands/convertquestion-example-2.PNG)

**Syntax**

```
convertquestion <messageid> <channel> <note> (optional)
```              

**Arguments**      

- `messageid`: The message id for the message to be converted
- `channel`: The channel the message id is posted in
- `note`: Text to be added as a note to the question (optional)

## ConvertAnswer (Admin Only)

Convert a given message in a given channel to a new QuestionBot answer.

**Example**
![Example](/.github/assets/commands/convertanswer-example-1.PNG)

**Syntax**

```
convertanswer <question> <messageid> <channel> 
```              

**Arguments**      

- `question`: The question id to add the answer to
- `messageid`: The message id for the message to be converted
- `channel`: The channel the message id is posted in

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
