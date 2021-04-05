import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { SubmitListingComponent } from './submit-listing.component';

describe('SubmitListingComponent', () => {
  let component: SubmitListingComponent;
  let fixture: ComponentFixture<SubmitListingComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ SubmitListingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubmitListingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
