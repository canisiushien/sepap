import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompteValidationComponent } from './compte-validation.component';

describe('CompteValidationComponent', () => {
  let component: CompteValidationComponent;
  let fixture: ComponentFixture<CompteValidationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CompteValidationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CompteValidationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
