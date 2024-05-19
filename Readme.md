## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

# CN311 OS Project
## About the Project
- This project is a using socket and thread to create program that can send and receive data between server and client. 
- The server will receive data(Image) from client and convert it to text art then send it back to client. 
- The client will receive the text art, display it on the screen and save it to a file.

## Running the Project
- Open the project terminal and run the server from `bin` by typing 
```bash
$ java App server
```
- Open another terminal and run the client from `bin` by typing 
```bash
$ java App client <image_path>
```
- The client will save the text art to a file named `output.txt`