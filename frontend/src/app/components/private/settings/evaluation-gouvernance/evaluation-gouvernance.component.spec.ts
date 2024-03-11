import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EvaluationGouvernanceComponent } from './evaluation-gouvernance.component';

describe('EvaluationGouvernanceComponent', () => {
  let component: EvaluationGouvernanceComponent;
  let fixture: ComponentFixture<EvaluationGouvernanceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EvaluationGouvernanceComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EvaluationGouvernanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
