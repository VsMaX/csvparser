import { CsvparserPage } from './app.po';

describe('csvparser App', () => {
  let page: CsvparserPage;

  beforeEach(() => {
    page = new CsvparserPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
