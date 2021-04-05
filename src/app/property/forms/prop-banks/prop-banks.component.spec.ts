import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { PropBanksComponent } from './prop-banks.component';

describe('PropBanksComponent', () => {
  let component: PropBanksComponent;
  let fixture: ComponentFixture<PropBanksComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ PropBanksComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PropBanksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
