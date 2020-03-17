import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NoSecurityProfileComponent } from './no-security-profile.component';

describe('NoSecurityProfileComponent', () => {
  let component: NoSecurityProfileComponent;
  let fixture: ComponentFixture<NoSecurityProfileComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NoSecurityProfileComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NoSecurityProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
