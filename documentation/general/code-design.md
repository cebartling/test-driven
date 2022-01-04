# Code design thoughts


## January 4, 2022

### Test code coverage as an initial indicator of test-first TDD behavior

I use **test code coverage** as one of the first metrics to understanding a codebase. If a codebase has been driven with _test-first test-driven development_, where isolated unit tests exist for all production code, then the test code coverage will be very high for that codebase by default. Test code coverage has a strong correlation to test-first test-driven development behaviors. Unit tests are used to prove _basic correctness_ of each piece of code and to guide code design. Unit tests do not check how things work together... that would be an integration test, not a unit test. **Unit tests exert cognitive pressure on the developer to abide by a set of design principles (SOLID, GRASP, others). A unit of code that is testable typically coincides with these design principles.**
 
Test code coverage only ensures that every line of code has been exercised--it says nothing about code quality. "Untested code is buggy code", and as such test code coverage only ensures the code has been exercised and does not fail a basic correctness check. Exercising error handling code in an isolated unit test is far more desirable than discovering a defect in the error handling code in production because I could not exercise that error handling code in other higher level tests. I care much less about test code coverage for any sort of integrated testing. Therefore, I would start with test code coverage metric. It’s probably already being captured by Sonar for all your code repositories. It will be important to ensure that code coverage metric that we use is only for the unit tests. I don’t want integration tests part of that metric value.
 
### Integrated tests

_Integrated tests_ are all the tests above the base of unit tests in the Testing Pyramid. These tests can take the form of integration tests, API tests, behavior tests, acceptance tests, or end-to-end tests. In the case of developer-written integration tests, these tests exist to prove that the collection of components which were initially created with unit tests, actually works when integrated together. These tests fire a tracer bullet through all the components of the stack to ensure everything wires together properly. I see these sorts of tests commonly on the server-side. Behavior tests, such as those espoused by BDD using something like Cucumber and Gherkin, are great for both specifying requirements and being able to be exercised to verify the programs behavior matches the requirements. But behavior tests are not unit tests.


## October 11, 2021
I was recently helping a developer to successfully unit test a Spring Framework WebMVC controller. This controller was designed without the benefit of unit tests. It contained several responsibilities in the form of private methods that really didn't belong in the controller. I see this a lot, even with my own code. Our first implementations are hardly ever the optimal implementation. It was only after writing some of the first unit tests that the controller started to expose these code smells. Unit testing forces us to face the reality of our coding efforts and to _smell the code smells_ if present. 

In this case, the controller implemented some transformation logic that clearly belongs in a factory method or mapper class, away from the controller. Again, this happens a lot in development. We build functionality in one class, only to find that the responsibility doesn't really belong to that class. Unit testing helps to identify these misplaced responsibilities. Refactoring the responsibilities to new implementations is easily done once a set of unit tests are in place to support safe and confident refactoring.


## September 30, 2021
A group that I am coaching had a question about a Spring Boot WebFlux controller that was particularly difficult to test. Upon inspection, we determined that the controller was instantiating an object and that after writing some unit tests, it became clear that the tests would like to control that instantiated object. 

This happens a lot with test-driven development. To obtain test code coverage, you need to control an object that you don't have any control over how it's instantiated currently. You can capture it via an argument captor and verify it in the test after the fact, but if you need to set up the object in such a way that it determines an alternative pathway, this can prove to be difficult. 

In this case, the responsibility of instantiating the object could be moved to a factory and the factory  dependency injected into the controller instance. Now we have an _interception point_ that the unit tests can control for instantiating the object in the configuration that is needed for covering an execution path through the code. The factory can be mocked and injected into the controller and thus the object creation strategy can then be controlled within the test suite for the controller.