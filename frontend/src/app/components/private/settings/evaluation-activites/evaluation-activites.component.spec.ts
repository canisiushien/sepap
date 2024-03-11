import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EvaluationActivitesComponent } from './evaluation-activites.component';

describe('EvaluationActivitesComponent', () => {
  let component: EvaluationActivitesComponent;
  let fixture: ComponentFixture<EvaluationActivitesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EvaluationActivitesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EvaluationActivitesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
