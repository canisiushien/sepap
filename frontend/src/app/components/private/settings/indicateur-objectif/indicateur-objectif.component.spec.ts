import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IndicateurObjectifComponent } from './indicateur-objectif.component';

describe('IndicateurObjectifComponent', () => {
  let component: IndicateurObjectifComponent;
  let fixture: ComponentFixture<IndicateurObjectifComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IndicateurObjectifComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(IndicateurObjectifComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
