import { Observable, Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged, startWith, switchMap } from 'rxjs/operators';

class SomeClass {
  someFunction(val: string): string {
    return val;
  }
}

describe('Observable and Subject specs', () => {
  describe('piping multiple RxJS operators', () => {
    let subject$: Subject<string>;
    let observable$: Observable<string>;
    let emittedValues: Array<string>;
    let spiedFunction: jasmine.Spy;
    let someClassInstance: SomeClass;
    const lastEmittedValue = 'supercalifragilisticexpialidocious';

    beforeEach((done: DoneFn) => {
      someClassInstance = new SomeClass();
      spiedFunction = spyOn(someClassInstance, 'someFunction').and.callThrough();
      emittedValues = new Array<string>();
      subject$ = new Subject<string>();
      observable$ = subject$.pipe(
        startWith('foobar'),
        debounceTime(500),
        distinctUntilChanged(),
        switchMap((source) => {
          return someClassInstance.someFunction(source);
        })
      );
      observable$.subscribe((next: string) => emittedValues.push(next));
      subject$.next('barfoo');
      subject$.next('BARFOO');
      subject$.next(lastEmittedValue);
      // eslint-disable-next-line no-undef
      setTimeout(() => {
        done();
      }, 600);
    });

    it('should emit individual characters', () => {
      expect(emittedValues.length).toBe(lastEmittedValue.length);
    });

    it('should invoke the spy multiple times', () => {
      // expect(spiedFunction).toHaveBeenCalledWith('foobar');
      expect(spiedFunction).toHaveBeenCalledWith(lastEmittedValue);
    });
  });
});
