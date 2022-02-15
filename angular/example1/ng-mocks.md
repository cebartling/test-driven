# ng-mocks

## Introduction

The [ng-mocks](https://ng-mocks.sudo.eu/) module is used testing library which helps with mocking services, components, directives, pipes and modules in tests for Angular applications. When we have a noisy child component, or any other annoying dependency, ng-mocks has tools to turn these declarations into their mocks, keeping interfaces as they are, but suppressing their implementation.


## Mocking child components

When writing isolated unit tests for Angular components, it is not uncommon to encounter the need to declare child components in your unit test setup. 
The following is an error message you will see regularly when you have not declared a child component in the testing module for the specification suite.

```
'NG0304: 'app-dependency' is not a known element:
1. If 'app-dependency' is an Angular component, then verify that it is part of this module.
2. If 'app-dependency' is a Web Component then add 'CUSTOM_ELEMENTS_SCHEMA' to the '@NgModule.schemas' of this component to suppress this message.'
```

We can clear this up by adding the `DependencyComponent` class to the `declarations` list when configuring the testing module of the `TestBed`.

```typescript
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

describe('TargetComponent', () => {
  let component: TargetComponent;
  let fixture: ComponentFixture<TargetComponent>;

  beforeEach(waitForAsync(() =>   {
    TestBed.configureTestingModule({
      declarations: [
        // our component for testing
        TargetComponent,

        // the annoying dependency
        DependencyComponent,
      ],
    }).compileComponents();
  }));

  beforeEach(() =>   {
    fixture = TestBed.createComponent(TargetComponent);
    component = fixture.componentInstance;
  });
  
  // The rest of the specification suite has been redacted for clarity
});
```

This solves our immediate problem, but this can get out of hand when the child component hierarchy gets deep and the children components have dependencies on providers that are not used by the component under test. 
We also want to enforce isolation with our Jasmine specifications and declaring _real_ dependent child components in the `declarations` array of the testing module is tightly coupling our specs to component implementations other than the component under test.

The *ng-mocks* module helps with this conundrum by mocking out the interface for any child components that are included in the `declarations` array of the testing module.
In our example, we want to mock the `DependencyComponent`.

```typescript
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { MockComponent } from 'ng-mocks';

describe('TargetComponent', () => {
  let component: TargetComponent;
  let fixture: ComponentFixture<TargetComponent>;

  beforeEach(waitForAsync(() =>   {
    TestBed.configureTestingModule({
      declarations: [
        // our component for testing
        TargetComponent,

        // the annoying dependency, now mocked with ng-mocks!
        MockComponent(DependencyComponent),
      ],
    }).compileComponents();
  }));

  beforeEach(() =>   {
    fixture = TestBed.createComponent(TargetComponent);
    component = fixture.componentInstance;
  });
  
  // The rest of the specification suite has been redacted for clarity
});
```

The `MockComponent` function is used in several Jasmine specs in this example:
- [map-view.component.spec.ts](./src/app/views/map-view/__tests__/map-view.component.spec.ts)
- [app.component.spec.ts](./src/app/__tests__/app.component.spec.ts)  

For more information on using the `MockComponent` function, see the [documentation](https://ng-mocks.sudo.eu/api/MockComponent).


## Further reading

- [Towards Better Testing In Angular. Part 1 — Mocking Child Components](https://medium.com/@abdul_74410/towards-better-testing-in-angular-part-1-mocking-child-components-b51e1fd571da)
- [Towards Better Testing In Angular. Part 2 — Service Injection](https://medium.com/@abdul_74410/towards-better-testing-in-angular-part-2-service-injection-c87b1fede954)
- [Towards Better Testing In Angular. Part 3— Inputs & Outputs](https://medium.com/@abdul_74410/towards-better-testing-in-angular-part-3-inputs-outputs-e8ed361cdad6)

