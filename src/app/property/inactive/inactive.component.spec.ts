import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { InactiveComponent } from './inactive.component';

describe('InactiveComponent', () => {
  let component: InactiveComponent;
  let fixture: ComponentFixture<InactiveComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ InactiveComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InactiveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
