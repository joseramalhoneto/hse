export class Currency{
  base?: string;
  rates?: string[]

  constructor(
    name?: string,
    rates?: string[]
  ) {
      this.base = name,
      this.rates = rates
    }
}
