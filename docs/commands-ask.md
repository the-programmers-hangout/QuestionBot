# Ask Commands

These commands are used to manage new and already existing questions questions

## Table of Contents

- [Ask](#ask)
- [Question](#question)

## Ask (Admin Only)

Asks the guild a question

**Syntax**

```
$addanswer <question> | <notes>
```              

**Arguments**      

- `question`: The question you're asking
- `notes` (optional): The question notes, if any

## Question

Manages an already asked question

**Syntax**

```
$question <action> <id> (<question> | <notes>)
```      

**Arguments**

- `action`: The action to perform, either `edit` or `delete`
    - `edit`: Edits the given question id with the question | notes.
    - `delete`: Deletes the question
- `id`: The question id to perform said action on
- `question` (if action = edit): The question you're asking
- `notes` (optional, if action = edit): The question notes, if any
