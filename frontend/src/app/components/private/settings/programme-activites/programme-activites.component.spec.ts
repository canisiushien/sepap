import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProgrammeActivitesComponent } from './programme-activites.component';

describe('ProgrammeActivitesComponent', () => {
  let component: ProgrammeActivitesComponent;
  let fixture: ComponentFixture<ProgrammeActivitesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProgrammeActivitesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProgrammeActivitesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
