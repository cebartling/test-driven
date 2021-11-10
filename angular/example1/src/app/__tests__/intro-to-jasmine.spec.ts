class Barfoo {
  constructor(private _name: string) {}

  get name(): string {
    return this._name;
  }

  fetchData(id: string): string[] {
    return ['RESULT1', 'RESULT2'];
  }
}

class Foobar {
  constructor(private barfoo: Barfoo) {}

  get barfooName(): string {
    return this.barfoo.name;
  }

  getData(id: string): string[] {
    return this.barfoo.fetchData(id);
  }
}

describe('Introduction to Jasmine Framework specs', () => {
  const expectedId = '8d7fa8d7a876d7fa69';
  let barfoo: Barfoo;
  let foobar: Foobar;

  describe('using spyOn', () => {
    let actual: string[];
    const expectedResult = ['result1'];
    let spy: jasmine.Spy;

    beforeEach(() => {
      barfoo = new Barfoo('barfoo');
      foobar = new Foobar(barfoo);
      spy = spyOn(barfoo, 'fetchData').withArgs(expectedId).and.returnValue(expectedResult);
      // spyOn(barfoo, 'fetchData').withArgs(expectedId).and.returnValue(expectedResult);
      actual = foobar.getData(expectedId);
    });

    it('should invoke Barfoo.fetchData', () => {
      // expect(barfoo.fetchData).toHaveBeenCalledWith(expectedId);
      expect(spy).toHaveBeenCalledWith(expectedId);
    });

    it('should return the result from the Barfoo.fetchData invocation', () => {
      expect(actual).toEqual(expectedResult);
    });
  });

  describe('using spyOnProperty', () => {
    let actual: string;
    const expectedResult = 'barfoo';
    let propertySpy: jasmine.Spy;

    beforeEach(() => {
      barfoo = new Barfoo(expectedResult);
      foobar = new Foobar(barfoo);
      propertySpy = spyOnProperty(barfoo, 'name', 'get').and.callThrough();
      actual = foobar.barfooName;
    });

    it('should invoke Barfoo.name getter', () => {
      expect(propertySpy).toHaveBeenCalled();
    });

    it('should return the name used when constructing the Barfoo instance', () => {
      expect(actual).toEqual(expectedResult);
    });
  });

  describe('using jasmine.createSpy', () => {
    let actual: string[];
    const expectedResult = ['result1'];

    beforeEach(() => {
      barfoo = new Barfoo('barfoo');
      barfoo.fetchData = jasmine
        // .createSpy('fetchData', barfoo.fetchData)
        .createSpy()
        .withArgs(expectedId)
        .and.returnValue(expectedResult);
      // .and.callThrough();
      foobar = new Foobar(barfoo);
      actual = foobar.getData(expectedId);
    });

    it('should invoke Barfoo.fetchData', () => {
      expect(barfoo.fetchData).toHaveBeenCalledWith(expectedId);
    });

    it('should return the result from the Barfoo.fetchData invocation', () => {
      expect(actual).toEqual(expectedResult);
    });
  });

  describe('using jasmine.createSpyObj', () => {
    let actual: string[];
    const expectedResult = ['result1'];

    beforeEach(() => {
      barfoo = jasmine.createSpyObj('barfoo', {
        fetchData: expectedResult,
      });
      foobar = new Foobar(barfoo);
      actual = foobar.getData(expectedId);
    });

    it('should invoke Barfoo.fetchData', () => {
      expect(barfoo.fetchData).toHaveBeenCalledWith(expectedId);
    });

    it('should return the result from the Barfoo.fetchData invocation', () => {
      expect(actual).toEqual(expectedResult);
    });
  });
});
