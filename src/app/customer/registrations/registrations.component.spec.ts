import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { RegistrationsComponent } from './registrations.component';

describe('RegistrationsComponent', () => {
  let component: RegistrationsComponent;
  let fixture: ComponentFixture<RegistrationsComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ RegistrationsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegistrationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
