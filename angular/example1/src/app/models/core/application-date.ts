import { DateTime, ToISOTimeOptions } from 'luxon';

export class ApplicationDate {
  private dateTime: DateTime;

  /**
   * Use static factory methods to create the appropriate instance of this class.
   *
   * @private
   */
  private constructor() {
    this.dateTime = DateTime.now().toUTC();
  }

  /**
   * Creates a new ApplicationDate that represents the current timestamp.
   */
  public static now(): ApplicationDate {
    return new ApplicationDate();
  }

  /**
   * Output this ApplicationDate in ISO8601 format.
   */
  public toISO8601Format() {
    const isoOptions = {
      includeOffset: true,
      includePrefix: false,
      suppressMilliseconds: false,
      suppressSeconds: false,
      format: 'extended',
    } as ToISOTimeOptions;
    return this.dateTime.toISO(isoOptions);
  }
}
