import { NewAngularAppPage } from './app.po';

describe('new-angular-app App', () => {
  let page: NewAngularAppPage;

  beforeEach(() => {
    page = new NewAngularAppPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!!');
  });
});
