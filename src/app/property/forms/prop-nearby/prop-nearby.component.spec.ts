import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { PropNearbyComponent } from './prop-nearby.component';

describe('PropNearbyComponent', () => {
  let component: PropNearbyComponent;
  let fixture: ComponentFixture<PropNearbyComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ PropNearbyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PropNearbyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
