import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {TranslationsEditComponent} from './translations-edit.component';

describe('TranslationsEditComponent', () => {
  let component: TranslationsEditComponent;
  let fixture: ComponentFixture<TranslationsEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [TranslationsEditComponent]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TranslationsEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
