import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EtatsActiviteDetailsComponent } from './etats-activite-details.component';

describe('EtatsActiviteDetailsComponent', () => {
  let component: EtatsActiviteDetailsComponent;
  let fixture: ComponentFixture<EtatsActiviteDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EtatsActiviteDetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EtatsActiviteDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
