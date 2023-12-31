# kays-collection-online
## OpenAPI Specification for kays-collection-online (Magic Realm API)

## Overview

Welcome to the Magic Realm API! This API, defined by the OpenAPI 3.0.1 specification, provides a comprehensive suite of endpoints for managing wizards, artifacts, and users in a magical universe. This document serves as a guide to using the API, detailing the available endpoints, their functions, and how to interact with them.

### Version
- v1

### Base URL
- `http://localhost`

## API Endpoints

### Wizards

#### Find Wizard By ID
- **GET** `/api/v1/wizards/{wizardId}`
    - Retrieves a wizard by their unique ID.

#### Update Wizard
- **PUT** `/api/v1/wizards/{wizardId}`
    - Updates the details of an existing wizard.

#### Delete Wizard
- **DELETE** `/api/v1/wizards/{wizardId}`
    - Removes a wizard from the database.

#### Assign Artifact to Wizard
- **PUT** `/api/v1/wizards/{wizardId}/artifacts/{artifactId}`
    - Assigns an artifact to a wizard.

#### List All Wizards
- **GET** `/api/v1/wizards`
    - Retrieves a list of all wizards.

#### Add Wizard
- **POST** `/api/v1/wizards`
    - Adds a new wizard to the database.

### Users

#### Find User By ID
- **GET** `/api/v1/users/{userId}`
    - Retrieves a user by their unique ID.

#### Update User
- **PUT** `/api/v1/users/{userId}`
    - Updates the details of an existing user.

#### Delete User
- **DELETE** `/api/v1/users/{userId}`
    - Removes a user from the database.

#### List All Users
- **GET** `/api/v1/users`
    - Retrieves a list of all users.

#### Add User
- **POST** `/api/v1/users`
    - Adds a new user to the database.

### Artifacts

#### Find Artifact By ID
- **GET** `/api/v1/artifacts/{artifactId}`
    - Retrieves an artifact by its unique ID.

#### Update Artifact
- **PUT** `/api/v1/artifacts/{artifactId}`
    - Updates the details of an existing artifact.

#### Delete Artifact
- **DELETE** `/api/v1/artifacts/{artifactId}`
    - Removes an artifact from the database.

#### List All Artifacts
- **GET** `/api/v1/artifacts`
    - Retrieves a list of all artifacts.

#### Add Artifact
- **POST** `/api/v1/artifacts`
    - Adds a new artifact to the database.

### Authentication

#### User Login
- **POST** `/api/v1/users/login`
    - Authenticates a user and provides login information.

## Schemas

### Result
- Standard response object for API requests, containing `flag`, `code`, `message`, and `data`.

### WizardDto
- Data transfer object for Wizard, including `id`, `name`, and `numberOfArtifacts`.

### UserDto
- Data transfer object for User, including `id`, `username`, `enabled`, and `roles`.

### ArtifactDto
- Data transfer object for Artifact, including `id`, `name`, `description`, `imageUrl`, and `owner`.

### AppUser
- Data transfer object for application user, including `id`, `username`, `password`, `enabled`, and `roles`.

## Response Codes

Each endpoint may return the following response codes:

- **200 OK** - Request succeeded.
- **400 Bad Request** - Request was invalid or cannot be served.
- **401 Unauthorized** - Authentication failed or user does not have permissions.
- **403 Forbidden** - User does not have necessary permissions.
- **404 Not Found** - Requested resource was not found.
- **500 Internal Server Error** - Internal server error occurred.

## Usage

To use the API, send HTTP requests to the endpoints defined above with the required parameters. Refer to the specific endpoint documentation for required fields and detailed examples.
