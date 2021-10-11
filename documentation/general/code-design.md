# Code design thoughts

## October 11, 2021
I was recently helping a developer to successfully unit test a Spring Framework WebMVC controller. This controller was designed without the benefit of unit tests. It contained several responsibilities in the form of private methods that really didn't belong in the controller. I see this a lot, even with my own code. Our first implementations are hardly ever the optimal implementation. It was only after writing some of the first unit tests that the controller started to expose these code smells. Unit testing forces us to face the reality of our coding efforts and to _smell the code smells_ if present. 

In this case, the controller implemented some transformation logic that clearly belongs in a factory method or mapper class, away from the controller. Again, this happens a lot in development. We build functionality in one class, only to find that the responsibility doesn't really belong to that class. Unit testing helps to identify these misplaced responsibilities. Refactoring the responsibilities to new implementations is easily done once a set of unit tests are in place to support safe and confident refactoring.


## September 30, 2021
A group that I am coaching had a question about a Spring Boot WebFlux controller that was particularly difficult to test. Upon inspection, we determined that the controller was instantiating an object and that after writing some unit tests, it became clear that the tests would like to control that instantiated object. 

This happens a lot with test-driven development. To obtain test code coverage, you need to control an object that you don't have any control over how it's instantiated currently. You can capture it via an argument captor and verify it in the test after the fact, but if you need to set up the object in such a way that it determines an alternative pathway, this can prove to be difficult. 

In this case, the responsibility of instantiating the object could be moved to a factory and the factory  dependency injected into the controller instance. Now we have an _interception point_ that the unit tests can control for instantiating the object in the configuration that is needed for covering an execution path through the code. The factory can be mocked and injected into the controller and thus the object creation strategy can then be controlled within the test suite for the controller.