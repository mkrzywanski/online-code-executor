# online-code-executor

[![codecov](https://codecov.io/gh/mkrzywanski/online-code-executor/branch/main/graph/badge.svg?token=DIH6TGEU2U)](https://codecov.io/gh/mkrzywanski/online-code-executor)

<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li><a href="#about-the-project">Usage</a></li>
    <li><a href="#built-with">Built with</a></li>
    <li><a href="#getting-started">Getting started</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>

## About the project

This repository contains frontend and backend for application that is used to compile/execute code.
Supported languages are :

1. Java
2. Kotlin
3. Groovy

The code is compiled in-memory or in a temporary disk storage.

## Built with 

1. Java 14
2. Micronaut
3. Groovy/Spock
4. ReactJS
5. Docker

## Getting Started

Easiest option to launch entire application is to use `start.sh` script. It will automatically launch docker-compose and build all needed images.

```bash
./start.sh
```

## Contact

Your Name - [@m_krzyw](https://twitter.com/m_krzyw) - michal.krzywanski1@gmail.com