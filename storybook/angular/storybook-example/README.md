# Storybook and Angular Example

- [StoryBook Angular tutorial](https://storybook.js.org/tutorials/intro-to-storybook/angular/en/get-started/)


## Development cadence

This Angular project has Storybook integrated into it. Storybook is useful for helping to facilitate the development of
Angular components in isolation, along with Jasmine specifications. This is colloquially known as a _component-driven
methodology_.

Typically I start the Storybook server and run the Jasmine spec suite while creating new Angular components. I will
start with the leaf nodes of the component hierarchy and work upward until I get to root level routable view. The views
in this project are really meant for retrieving all the view's data from the remote API. I will build out the HTML
template for component in Storybook, allowing me to iterate quickly on the isolated component. Any behavior needed in an
Angular component is driven with Jasmine specifications. Using both techniques allows me to avoid starting up the
Webpack dev server and the JSON API server until I have a collection of components and the view component fairly well
fleshed out. My development cadence is definitely streamlined by using Storybook and TDD together.

### Node.js runtime environment

- Run `nvm use` to set the configured Node.js runtime environment.
  - Follow the directions if that command fails due to not having the correct version of Node.js runtime.

### Storybook server

- Run `ng run storybook` to start the Storybook server and open a browser to the Storybook website locally.

### Running unit tests

- Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).
- Run `ng run test:coverage` to execute the unit tests with code coverage.

### Development server

Again, I don't typically run the Webpack dev server and JSON API server until I have most of component hierarchy fleshed
out in Storybook and Jasmine specifications. Once I get to the point of exploratory testing the UI, using the Webpack
dev server and the JSON API server is a necessity.

1. Run `ng run json-server` to run the JSON API server. More information on the
   json-server [here](https://github.com/typicode/json-server).
1. Run `ng serve` to run the Webpack dev server.
1. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

## Angular

### Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also
use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

### Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory.

### Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via a platform of your choice. To use this command, you need to first add a package that implements end-to-end testing capabilities.

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI Overview and Command Reference](https://angular.io/cli) page.
