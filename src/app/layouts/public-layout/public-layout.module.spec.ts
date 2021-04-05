import { PublicLayoutModule } from './public-layout.module';

describe('PublicLayoutModule', () => {
  let publicLayoutModule: PublicLayoutModule;

  beforeEach(() => {
    publicLayoutModule = new PublicLayoutModule();
  });

  it('should create an instance', () => {
    expect(publicLayoutModule).toBeTruthy();
  });
});
