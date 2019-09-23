# Ask Commands

These commands are used to manage new and already existing questions questions

## Table of Contents

- [Ask](#ask)
- [Question](#question)

## Ask (Admin Only)

Asks the guild a question

**Example**

![Example](/.github/assets/commands/ask-example.png)

**Syntax**

```
$addanswer <question> | <notes>
```              

**Arguments**      

- `question`: The question you're asking
- `notes` (optional): The question notes, if any

## Question (Admin Only)

Manages an already asked question

**Example**

Editing an already existing question

![Example](/.github/assets/commands/edit-example-1.png)
![Example](/.github/assets/commands/edit-example-2.png)

Deleting a question (also deletes all answers)

![Example](/.github/assets/commands/edit-delete-example.png)

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
